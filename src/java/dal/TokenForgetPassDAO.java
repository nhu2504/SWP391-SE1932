/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.TokenForgetPass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author NGOC ANH
 */
public class TokenForgetPassDAO {

    public String getFormatDate(LocalDateTime myDate) {
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDate = myDate.format(myFormat);
        return formatDate;
    }

    public boolean insertTokenForget(TokenForgetPass tokenForget) {
        String sql = "insert into TokenForgetPassword(token,expTime,isUsed,userID)\n"
                + "values\n"
                + "(?, DATEADD(MINUTE, 5, GETDATE()),?,?)";
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tokenForget.getToken());
            
            ps.setBoolean(2, tokenForget.isIsUsed());
            ps.setInt(3, tokenForget.getUserId());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
             e.printStackTrace(); 
        }
        return false;
    }
     public TokenForgetPass getByToken(String token) {
        String sql = "SELECT * FROM TokenForgetPassword WHERE token = ?";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TokenForgetPass tfp = new TokenForgetPass();
                    tfp.setToken(rs.getString("token"));
                    tfp.setExpTime(rs.getTimestamp("expTime").toLocalDateTime());
                    tfp.setIsUsed(rs.getBoolean("isUsed"));
                    tfp.setUserId(rs.getInt("userID"));
                    return tfp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 2. Đánh dấu token đã dùng
    public boolean markTokenUsed(String token) {
        String sql = "UPDATE TokenForgetPassword SET isUsed = 1 WHERE token = ?";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Xoá token hết hạn hoặc đã dùng (optional)
    public int deleteExpiredTokens() {
        String sql = "DELETE FROM TokenForgetPassword WHERE expTime < GETDATE() OR isUsed = 1";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void main(String[] args) {
        // Tạo DAO
        TokenForgetPassDAO dao = new TokenForgetPassDAO();

        // Tạo token test
        TokenForgetPass token = new TokenForgetPass();
        token.setToken("abc123xyz");
        
        token.setIsUsed(false);
        token.setUserId(1); // giả sử user có ID = 1

        // Gọi phương thức insert
        boolean success = dao.insertTokenForget(token);

        // In kết quả
        if (success) {
            System.out.println("Chèn token thành công!");
        } else {
            System.out.println("Chèn token thất bại!");
        }
    }

}
