/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Register;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author DO NGOC ANH HE180661
 */
public class RegisterDAO {

    public boolean register(String fullName, String phone, String email, String gender, Date birth,
             String school, String classAtSchool, String parentPhone, String parentEmail,
            String avatar, boolean confirm) {
        boolean check = false;
        String query = "  insert into TutoringRegistrationPending(FullName,Phone,Email,\n"
                + "  Gender,BirthDate,School,Class,ParentPhone,\n"
                + "  ParentEmail,Avatar,Confirmed)\n"
                + "  values\n"
                + "  (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fullName);
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.setString(4, gender);
            ps.setDate(5, (java.sql.Date) birth);
            ps.setString(6, school);
            ps.setString(7, classAtSchool);
            ps.setString(8, parentPhone);
            ps.setString(9, parentEmail);
            ps.setString(10, avatar);
            ps.setBoolean(11, confirm);
            
            check = ps.executeUpdate()>0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;

    }
}
