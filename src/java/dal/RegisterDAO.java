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
            boolean confirm, String interestCourses) {
        boolean check = false;
        //lệnh insert vào 12 cột tương ứng từ 1 tới 12
        String query = """
                       insert into TutoringRegistrationPending(FullName,Phone,Email,Gender,BirthDate,School,AddressSchool,Class,ParentPhone,ParentEmail,UserIntro,Confirmed,InterestCourses)
                       values
                       (?,?,?,?,?,?,?,?,?,?,?,?,?)""";
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
            ps.setString(13, interestCourses);
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
                r.setInterestCourses(rs.getString("InterestCourses"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu xảy ra
        }

        return list; // Trả về danh sách phòng học
    }

    public boolean approveUserByProcedure(int regisID) {
        String sql = "{call sp_CreateAccount(?)}";
        try (Connection conn = new DBContext().connection; CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, regisID);
            cs.execute();
            return true; // thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // thất bại
        }
    }

    public boolean updateStatus(int id, String status) {
    String sql = "UPDATE TutoringRegistrationPending SET ApprovalStatus = ? WHERE RegistrationPendingID = ?";
    try (Connection conn = new DBContext().connection;
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, status);
        ps.setInt(2, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; // nếu có dòng nào được cập nhật thì thành công
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    public List<Register> getFilteredRegisters(String status, String keyword, String nameKeyword) {
    List<Register> list = new ArrayList<>();

    StringBuilder sql = new StringBuilder("SELECT * FROM TutoringRegistrationPending WHERE 1=1");

    if (status != null && !status.equalsIgnoreCase("all")) {
        sql.append(" AND ApprovalStatus = ?");
    }
    if (keyword != null && !keyword.trim().isEmpty()) {
        sql.append(" AND LOWER(InterestCourses) LIKE ?");
    }
    if (nameKeyword != null && !nameKeyword.trim().isEmpty()) {
        sql.append(" AND LOWER(FullName) LIKE ?");
    }

    sql.append(" ORDER BY CASE WHEN ApprovalStatus = 'Pending' THEN 0 ELSE 1 END, RegisterDate ASC");

    try (Connection conn = new DBContext().connection;
         PreparedStatement ps = conn.prepareStatement(sql.toString())) {

        int idx = 1;
        if (status != null && !status.equalsIgnoreCase("all")) {
            ps.setString(idx++, status);
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            ps.setString(idx++, "%" + keyword.toLowerCase() + "%");
        }
        if (nameKeyword != null && !nameKeyword.trim().isEmpty()) {
            ps.setString(idx++, "%" + nameKeyword.toLowerCase() + "%");
        }

        ResultSet rs = ps.executeQuery();
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
            r.setInterestCourses(rs.getString("InterestCourses"));
            list.add(r);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}
    
    public boolean isEmailExist(String email) {
    String sql = "SELECT COUNT(*) FROM [User] WHERE email = ?";
    try (Connection conn = new DBContext().connection;
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


public static void main(String[] args) {
        RegisterDAO dao = new RegisterDAO();
        List<Register> list = dao.getListRegister();

        if (list == null || list.isEmpty()) {
            System.out.println("Không có bản đăng ký nào được tìm thấy.");
        } else {
            System.out.println("Danh sách các bản đăng ký:");
            for (Register r : list) {
                System.out.println("ID: " + r.getRegisID());
                System.out.println("Họ tên: " + r.getFullName());
                System.out.println("Email: " + r.getEmail());
                System.out.println("SĐT: " + r.getPhone());
                System.out.println("Ngày đăng ký: " + r.getRegisDate());
                System.out.println("Trạng thái: " + r.getApprovalStatus());
                System.out.println("Giới tính: " + r.getGender());
                System.out.println("Ngày sinh: " + r.getBirth());
                System.out.println("Trường: " + r.getSchool());
                System.out.println("Địa chỉ trường: " + r.getAddress());
                System.out.println("Lớp: " + r.getClassAtSchool());
                System.out.println("SĐT phụ huynh: " + r.getParentPhone());
                System.out.println("Email phụ huynh: " + r.getParentEmail());
                System.out.println("Người giới thiệu (ID): " + r.getIdUserIntro());
                System.out.println("Khoá học quan tâm: " + r.getInterestCourses());
                System.out.println("Đã xác nhận: " + (r.isConfirm() ? "Có" : "Chưa"));
                System.out.println("----------------------------------------------------");
            }
        }
    }

}
