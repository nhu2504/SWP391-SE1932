package dal;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Ngày update: 30/06/2025 Người viết: Văn Thị Như
 *
 * Mô tả: DAO này xử lý các thao tác truy vấn liên quan đến học sinh, bao gồm
 * top học sinh, tổng số lượng học sinh và thống kê học sinh theo môn học.
 */
public class StudentDAO {

    /**
     * Lấy danh sách top 10 học sinh có đánh giá cao và có chứng chỉ (Certi)
     *
     * Điều kiện lọc: - Vai trò là học sinh (roleID = 3) - Có comment và rating
     * >= 4 - Có chứng chỉ không rỗng
     *
     * Sắp xếp theo: - Đánh giá giảm dần - Số lượng thành tích (tính bằng số dấu
     * chấm trong chuỗi certi) - Ngày comment gần nhất
     *
     * @return Danh sách Map đại diện cho học sinh, mỗi Map chứa thông tin như
     * tên, avatar, chứng chỉ, đánh giá...
     */
    public List<Map<String, Object>> getTopStudents() {
        List<Map<String, Object>> students = new ArrayList<>();
        String sql = """
            SELECT TOP 10 u.UserID, u.FullName, u.avatar, u.Certi, u.email, 
                   c.CommentText, c.CommentDate, c.Rating, 
                   (LEN(u.Certi) - LEN(REPLACE(u.Certi, '.', '')) + 1) AS AchievementCount
            FROM [User] u
            LEFT JOIN Comment c ON u.UserID = c.UserID
            WHERE u.roleID = 3 
              AND c.CommentText IS NOT NULL 
              AND c.Rating >= 4 
              AND u.Certi IS NOT NULL AND u.Certi <> ''
            ORDER BY c.Rating DESC, AchievementCount DESC, c.CommentDate DESC
        """;

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> student = new HashMap<>();
                student.put("userID", rs.getInt("UserID"));
                student.put("fullName", rs.getString("FullName"));
                student.put("avatar", rs.getString("avatar") != null ? rs.getString("avatar") : "images/fallback.png");
                student.put("certi", rs.getString("Certi"));
                student.put("email", rs.getString("email"));
                student.put("commentText", rs.getString("CommentText"));
                student.put("rating", rs.getInt("Rating"));
                student.put("achievementCount", rs.getInt("AchievementCount"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * Đếm tổng số học sinh trong hệ thống
     *
     * Điều kiện: roleID = 3 (học sinh)
     *
     * @return Tổng số học sinh (int)
     */
    public int getStudentCount() {
        String sql = "SELECT COUNT(*) AS total FROM [User] WHERE roleID = 3";
        try (Connection conn = new DBContext().connection; PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Lấy số lượng học sinh theo từng môn học
     *
     * Cách đếm: - Từ bảng Subjects -> TutoringClass -> ClassGroup ->
     * ClassGroup_Student - Đếm số học sinh đang tham gia lớp của từng môn
     *
     * @return Danh sách Object[] gồm: [0] = Tên môn học, [1] = Số lượng học
     * sinh
     */
    public List<Object[]> getStudentCountPerSubject() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT s.SubjectName, COUNT(cgs.StudentID) AS StudentCount
            FROM Subjects s
            LEFT JOIN TutoringClass tc ON s.SubjectID = tc.SubjectID
            LEFT JOIN ClassGroup cg ON tc.TutoringClassID = cg.TutoringClassID
            LEFT JOIN ClassGroup_Student cgs ON cg.ClassGroupID = cgs.ClassGroupID
            GROUP BY s.SubjectName
            ORDER BY s.SubjectName
        """;

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("SubjectName"),
                    rs.getInt("StudentCount")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Ngọc Anh
    public boolean updateSchoolClassDAO(int userId, int classId) {
        String deleteSql = "DELETE FROM TeacherClass WHERE UserID = ?";
        String insertSql = "INSERT INTO TeacherClass (UserID, SchoolClassID) VALUES (?, ?)";

        try (Connection conn = new DBContext().connection) {
            conn.setAutoCommit(false);

            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql); PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

                deleteStmt.setInt(1, userId);
                deleteStmt.executeUpdate();

                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, classId);
                insertStmt.executeUpdate();

                conn.commit();
                return true;
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
