/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author DO NGOC ANH HE180661
 */
public class RegisterDAO {
    //nhận vào các thông tin mà học sinh đăng kí và lưu trữ vào bảng TutoringRegistrationPending
    public boolean register(String fullName, String phone, String email, String gender, Date birth,
            String school,String address, String classAtSchool, String parentPhone, String parentEmail,
            int idUserIntro,
            boolean confirm) {
        boolean check = false;
        //lệnh insert vào 12 cột tương ứng từ 1 tới 12
        String query = """
                       insert into TutoringRegistrationPending(FullName,Phone,Email,Gender,BirthDate,School,AddressSchool,Class,ParentPhone,ParentEmail,UserIntro,Confirmed)
                       values
                       (?,?,?,?,?,?,?,?,?,?,?,?)""";
        //tạo kết nối, chuẩn bị câu lệnh sql
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fullName);
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.setString(4, gender);
            ps.setDate(5, (java.sql.Date) birth);
            ps.setString(6, school);
            ps.setString(7, address);
            ps.setString(8, classAtSchool);
            ps.setString(9, parentPhone);
            ps.setString(10, parentEmail);
            ps.setInt(11, idUserIntro);
            ps.setBoolean(12, confirm);
            //trả về số dòng bị ảnh hưởng, nếu thành công thì số dòng > 0, check=true
            check = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.getMessage();
        }
        return check;

    }

}
