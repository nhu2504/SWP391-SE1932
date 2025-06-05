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

public class TeacherDAO {
    // Lấy danh sách giáo viên (roleID = 2)
    public List<Map<String, Object>> getAllTeachers() {
        List<Map<String, Object>> teachers = new ArrayList<>();
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     // Câu SQL lấy thông tin giáo viên
                     "SELECT UserID, FullName, Gender, phone, email, avatar, Certi, Descrip, onlineStatus "
                             + "FROM [User] WHERE roleID = ?")) {
            ps.setInt(1, 2); // roleID = 2 là giáo viên
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> teacher = new HashMap<>();
                teacher.put("userID", rs.getInt("UserID"));
                teacher.put("fullName", rs.getString("FullName"));
                teacher.put("gender", rs.getString("Gender"));
                teacher.put("phone", rs.getString("phone"));
                teacher.put("email", rs.getString("email"));
                teacher.put("avatar", rs.getString("avatar") != null ? rs.getString("avatar") : "images/fallback.png");
                teacher.put("certi", rs.getString("Certi"));
                teacher.put("descrip", rs.getString("Descrip"));
                teacher.put("onlineStatus", rs.getBoolean("onlineStatus"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }
}