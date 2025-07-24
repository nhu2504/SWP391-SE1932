package dal;

import dal.DBContext;
import entity.SchoolClass;
import entity.User;
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
     * tên, avatar, chứng chỉ, đánh giá... Danh sách là 1 list chứa nhiều học
     * sinh, 1 học sinh là 1 map và string là key, object là value và dùng
     * object vì dùng kiểu dữ liệu j cx đc ví dụ int, string,date, ... đều đc
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

    public List<User> getStudentsByClassGroup(int classGroupID) {
        List<User> list = new ArrayList<>();
        String sql = """
    SELECT 
        u.UserID,
        u.FullName,
        u.Gender,
        u.BirthDate,
        u.Phone,
        u.Email,
        u.ParentPhone,
        u.ParentEmail,
        u.avatar,            
        u.SchoolID,
        sch.SchoolName,
        u.SchoolClassID,
        sc.ClassName
    FROM 
        ClassGroup_Student cs
    JOIN 
        [User] u ON cs.StudentID = u.UserID
    LEFT JOIN 
        School sch ON u.SchoolID = sch.SchoolID
    LEFT JOIN 
        SchoolClass sc ON u.SchoolClassID = sc.SchoolClassID
    WHERE 
        u.roleID = 3 AND cs.ClassGroupID = ?
    ORDER BY 
        u.FullName
""";

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, classGroupID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("UserID"));
                    u.setName(rs.getString("FullName"));
                    u.setGender(rs.getString("Gender"));
                    u.setBirth(rs.getDate("BirthDate"));
                    u.setPhone(rs.getString("Phone"));
                    u.setEmail(rs.getString("Email"));
                    u.setParentPhone(rs.getString("ParentPhone"));
                    u.setParentEmail(rs.getString("ParentEmail"));
                    u.setAvatar(rs.getString("avatar"));
                    u.setSchoolID(rs.getInt("SchoolID"));
                    u.setSchoolClassId(rs.getInt("SchoolClassID"));
                    u.setSchoolName(rs.getString("SchoolName"));            // ✅ tên trường
                    u.setSchoolClassName(rs.getString("ClassName"));

                    // Nếu bạn cần thêm EnrollDate hoặc IsActive, có thể tạo subclass hoặc lưu vào Map
                    list.add(u);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Như
    public List<User> getStudentsNotInClassGroupFullInfo(int classGroupId) {
        List<User> students = new ArrayList<>();
        String sql = """
        SELECT u.UserID, u.FullName, u.Gender, u.BirthDate, u.Phone, u.Email, u.Avatar,
               s.SchoolName, sc.ClassName, sc.SchoolClassID, s.SchoolID
        FROM [User] u
        LEFT JOIN School s ON u.SchoolID = s.SchoolID
        LEFT JOIN SchoolClass sc ON u.SchoolClassID = sc.SchoolClassID
        WHERE u.RoleID = 3
        AND NOT EXISTS (
            SELECT 1 FROM ClassGroup_Student cs
            WHERE cs.ClassGroupID = ? AND cs.StudentID = u.UserID
        )
        """;

        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, classGroupId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("UserID"));
                u.setName(rs.getString("FullName"));
                u.setGender(rs.getString("Gender"));
                u.setBirth(rs.getDate("BirthDate"));
                u.setPhone(rs.getString("Phone"));
                u.setEmail(rs.getString("Email"));
                u.setAvatar(rs.getString("Avatar"));
                u.setSchoolID(rs.getInt("SchoolID"));
                u.setSchoolName(rs.getString("SchoolName"));
                u.setSchoolClassName(rs.getString("ClassName"));
                u.setSchoolClassId(rs.getInt("SchoolClassID"));

                students.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    //ngoc anh

    public boolean updateStudentClass(int userId, int newClassId) {
    String sql = "UPDATE TeacherClass SET SchoolClassID = ? WHERE UserID = ?";
    try (Connection conn = new DBContext().connection;
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, newClassId);
        ps.setInt(2, userId);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    public SchoolClass getSchoolClassByStudentID(int studentId) throws Exception {
    String sql = "SELECT sc.SchoolClassID, sc.ClassName, sc.SchoolID FROM TeacherClass tc \n" +
"                 JOIN SchoolClass sc ON tc.SchoolClassID = sc.SchoolClassID \n" +
"                 WHERE tc.UserID = ?";
    try (Connection conn = new DBContext().connection;
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, studentId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            SchoolClass sc = new SchoolClass();
            sc.setSchoolClassID(rs.getInt("SchoolClassID"));
            sc.setClassName(rs.getString("ClassName"));
            sc.setSchoolID(rs.getInt("SchoolID"));
            return sc;
        }
    }
    return null;
}
}
