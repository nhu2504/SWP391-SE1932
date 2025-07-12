/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Register;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DO NGOC ANH HE180661
 */
public class RegisterDAO {

    //nhận vào các thông tin mà học sinh đăng kí và lưu trữ vào bảng TutoringRegistrationPending
    public boolean register(String fullName, String phone, String email, String gender, Date birth,
            String school, String address, String classAtSchool, String parentPhone, String parentEmail,
            Integer idUserIntro,
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
            if (idUserIntro != null) {
                ps.setInt(11, idUserIntro);
            } else {
                ps.setNull(11, java.sql.Types.INTEGER);
            }
            ps.setBoolean(12, confirm);
            //trả về số dòng bị ảnh hưởng, nếu thành công thì số dòng > 0, check=true
            check = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.getMessage();
        }
        return check;

    }

    public List<Register> getListRegister() {
        List<Register> list = new ArrayList<>();
        String sql = "SELECT * \n"
                + "FROM TutoringRegistrationPending\n"
                + "ORDER BY \n"
                + "    CASE \n"
                + "        WHEN ApprovalStatus = 'Pending' THEN 0 \n"
                + "        ELSE 1 \n"
                + "    END,\n"
                + "    RegisterDate ASC; "; // Câu lệnh SQL lấy toàn bộ 

        try (
                Connection conn = new DBContext().connection; // Mở kết nối đến DB
                 PreparedStatement ps = conn.prepareStatement(sql); // Tạo PreparedStatement
                 ResultSet rs = ps.executeQuery() // Thực thi truy vấn
                ) {
            while (rs.next()) {
                Register r = new Register();
                r.setRegisID(rs.getInt("RegistrationPendingID"));
                r.setFullName(rs.getString("FullName"));
                r.setPhone(rs.getString("Phone"));
                r.setEmail(rs.getString("Email"));
                r.setRegisDate(rs.getDate("RegisterDate"));
                r.setApprovalStatus(rs.getString("ApprovalStatus"));
                r.setGender(rs.getString("Gender"));
                r.setBirth(rs.getDate("BirthDate"));
                r.setSchool(rs.getString("School"));
                r.setAddress(rs.getString("AddressSchool"));
                r.setClassAtSchool(rs.getString("Class"));
                r.setParentPhone(rs.getString("ParentPhone"));
                r.setParentEmail(rs.getString("ParentEmail"));
                r.setConfirm(rs.getBoolean("Confirmed"));
                r.setIdUserIntro(rs.getInt("UserIntro"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu xảy ra
        }

        return list; // Trả về danh sách phòng học
    }

    public void approveUserByProcedure(int regisID) {
        String sql = "{call sp_ApprovePendingUser(?)}";
        try (Connection conn = new DBContext().connection; CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, regisID);
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int id, String status) {
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(
                "UPDATE TutoringRegistrationPending SET ApprovalStatus = ? WHERE RegistrationPendingID = ?"
        )) {
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
