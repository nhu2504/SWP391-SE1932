package dal;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ngày tạo: 23/06/2025 Người viết: Van Nhu
 */
public class StudentDAO {

    // Lấy top 10 học sinh có đánh giá và thành tích cao
    public List<Map<String, Object>> getTopStudents() {
        List<Map<String, Object>> students = new ArrayList<>();
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(
                // Câu SQL lấy top 10 học sinh
                "SELECT TOP 10 u.UserID, u.FullName, u.avatar, u.Certi, u.email, c.CommentText, c.CommentDate, c.Rating, "
                + "(LEN(u.Certi) - LEN(REPLACE(u.Certi, '.', '')) + 1) AS AchievementCount "
                + "FROM [User] u "
                + "LEFT JOIN Comment c ON u.UserID = c.UserID "
                + "WHERE u.roleID = 3 AND c.CommentText IS NOT NULL AND c.Rating >= 4 "
                + "AND u.Certi IS NOT NULL AND u.Certi <> '' "
                + "ORDER BY c.Rating DESC, (LEN(u.Certi) - LEN(REPLACE(u.Certi, '.', '')) + 1) DESC, c.CommentDate DESC"); ResultSet rs = ps.executeQuery()) {
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

    public boolean updateSchoolClassDAO(int userId, int classId) {
    String deleteSql = "DELETE FROM TeacherClass WHERE UserID = ?";
    String insertSql = "INSERT INTO TeacherClass (UserID, SchoolClassID) VALUES (?, ?)";

    try (Connection conn = new DBContext().connection) {
        conn.setAutoCommit(false);

        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

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

    public static void main(String[] args) {
        // Giả sử bạn có DAO chứa hàm updateSchoolClassDAO, tên là UserClassDAO
        StudentDAO dao = new StudentDAO();

        // Giả sử userID = 5 là giáo viên hoặc học sinh có thật
        int userId = 25;

        // Giả sử classId = 3 là một lớp học có thật trong bảng SchoolClass
        int classId = 14;

        boolean result = dao.updateSchoolClassDAO(userId, classId);

        if (result) {
            System.out.println("✅ Cập nhật lớp học thành công cho userID = " + userId);
        } else {
            System.out.println("❌ Cập nhật thất bại. Kiểm tra lại dữ liệu hoặc kết nối CSDL.");
        }
    }
}
