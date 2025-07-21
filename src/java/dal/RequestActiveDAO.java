/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author NGOC ANH
 */
public class RequestActiveDAO {
    public boolean insertRequest(String name, String email, Date birth,
            String school, String classAtSchool) {
        boolean check = false;
        //lệnh insert vào 12 cột tương ứng từ 1 tới 12
        String sql = "INSERT INTO RequestActive (NameStudent, email, Birth, School, Class) VALUES (?, ?, ?, ?, ?)";
        //tạo kết nối, chuẩn bị câu lệnh sql
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setDate(3, new java.sql.Date(birth.getTime())); 
            ps.setString(4, school);
            ps.setString(5, classAtSchool);
            
            //trả về số dòng bị ảnh hưởng, nếu thành công thì số dòng > 0, check=true
            check = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.getMessage();
        }
        return check;

    }
}
