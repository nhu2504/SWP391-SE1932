package dal;

import dal.DBContext;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//Văn Thị NHư - HE181329
public class TeacherDAO {
    // Lấy danh sách giáo viên (roleID = 2)
    public List<User> getAllTeachers() {
        List<User> teachers = new ArrayList<>();
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT UserID, FullName, Gender, phone, email, avatar, Certi, Descrip, onlineStatus, SchoolID "
                             + "FROM [User] WHERE roleID = ?")) {
            ps.setInt(1, 2); // roleID = 2 là giáo viên
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User teacher = new User();
                teacher.setId(rs.getInt("UserID"));
                teacher.setName(rs.getString("FullName"));
                teacher.setGender(rs.getString("Gender"));
                teacher.setPhone(rs.getString("phone"));
                teacher.setEmail(rs.getString("email"));
                teacher.setAvatar(rs.getString("avatar") != null ? rs.getString("avatar") : "images/fallback.png");
                teacher.setCerti(rs.getString("Certi"));
                teacher.setDescrip(rs.getString("Descrip"));
                teacher.setStatus(rs.getBoolean("onlineStatus") ? 1 : 0);
                teacher.setSchoolID(rs.getInt("SchoolID"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }
}