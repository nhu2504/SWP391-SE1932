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

public class StudentDAO {
    // Lấy top 10 học sinh có đánh giá và thành tích cao
    public List<Map<String, Object>> getTopStudents() {
        List<Map<String, Object>> students = new ArrayList<>();
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     // Câu SQL lấy top 10 học sinh
                     "SELECT TOP 10 u.UserID, u.FullName, u.avatar, u.Certi, u.email, c.CommentText, c.CommentDate, c.Rating, "
                             + "(LEN(u.Certi) - LEN(REPLACE(u.Certi, '.', '')) + 1) AS AchievementCount "
                             + "FROM [User] u "
                             + "LEFT JOIN Comment c ON u.UserID = c.UserID "
                             + "WHERE u.roleID = 3 AND c.CommentText IS NOT NULL AND c.Rating >= 4 "
                             + "AND u.Certi IS NOT NULL AND u.Certi <> '' "
                             + "ORDER BY c.Rating DESC, (LEN(u.Certi) - LEN(REPLACE(u.Certi, '.', '')) + 1) DESC, c.CommentDate DESC");
             ResultSet rs = ps.executeQuery()) {
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
}