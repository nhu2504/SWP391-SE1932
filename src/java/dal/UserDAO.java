package dal;

import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final DBContext dbContext = DBContext.getInstance();

    public User authenticate(String email, String password) throws SQLException {
        String sql = "SELECT UserID, Email, Password, UserName, Role, Avatar FROM Users WHERE Email = ? AND Password = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password); // Lưu ý: Mật khẩu nên được mã hóa trong thực tế
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setUserName(rs.getString("UserName"));
                user.setRole(rs.getString("Role"));
                user.setAvatar(rs.getString("Avatar"));
                return user;
            }
        }
        return null;
    }
}