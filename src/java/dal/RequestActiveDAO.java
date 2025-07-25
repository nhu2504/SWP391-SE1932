/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.RequestActive;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<RequestActive> getListRequest() {
        List<RequestActive> list = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM RequestActive\n"
                + "ORDER BY \n"
                + "    CASE \n"
                + "        WHEN stt = 'Pending' THEN 0\n"
                + "        ELSE 1\n"
                + "    END,\n"
                + "    DateRequest ASC;"; // Câu lệnh SQL lấy toàn bộ 

        try (
                Connection conn = new DBContext().connection; // Mở kết nối đến DB
                 PreparedStatement ps = conn.prepareStatement(sql); // Tạo PreparedStatement
                 ResultSet rs = ps.executeQuery() // Thực thi truy vấn
                ) {
            while (rs.next()) {
                RequestActive r = new RequestActive();
                r.setRaId(rs.getInt("raID"));
                r.setName(rs.getString("NameStudent"));
                r.setEmail(rs.getString("email"));
                r.setBirth(rs.getDate("Birth"));
                r.setSchool(rs.getString("School"));
                r.setClassAtSchool(rs.getString("Class"));
                r.setStatus(rs.getString("stt"));
                r.setDayRequest(rs.getDate("DateRequest"));
                
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu xảy ra
        }

        return list; // Trả về danh sách phòng học
    }
    public void approveRequest(int requestId) {
    String sql = "UPDATE RequestActive SET stt = 'Accepted' WHERE raID = ?";
    try (Connection conn = new DBContext().connection; // Mở kết nối đến DB
         PreparedStatement ps = conn.prepareStatement(sql);) {
        ps.setInt(1, requestId);
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public List<RequestActive> filterRequests(String keyword, String status) {
    List<RequestActive> list = new ArrayList<>();
    String sql = "SELECT * FROM RequestActive WHERE 1=1";

    if (keyword != null && !keyword.isEmpty()) {
        sql += " AND LOWER(NameStudent) LIKE ?";
    }

    if (status != null && !status.equals("all")) {
        sql += " AND stt = ?";
    }

    try (Connection conn = new DBContext().connection; // Mở kết nối đến DB
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        int index = 1;

        if (keyword != null && !keyword.isEmpty()) {
            ps.setString(index++, "%" + keyword.toLowerCase() + "%"); // tìm gần đúng, không phân biệt hoa thường
        }

        if (status != null && !status.equals("all")) {
            ps.setString(index++, status); // ví dụ: "Pending"
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            RequestActive r = new RequestActive(
                rs.getInt("raID"),
                rs.getString("NameStudent"),
                rs.getString("email"),
                rs.getDate("Birth"),
                rs.getString("School"),
                rs.getString("Class"),
                rs.getString("stt"),
                    rs.getDate("DateRequest")
            );
            list.add(r);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


    public static void main(String[] args) {
        RequestActiveDAO dao = new RequestActiveDAO();
        List<RequestActive> list = dao.getListRequest();

        if (list == null || list.isEmpty()) {
            System.out.println("Không có bản đăng ký nào được tìm thấy.");
        } else {
            System.out.println("Danh sách các bản đăng ký:");
            for (RequestActive r : list) {
                System.out.println("ID: " + r.getRaId());
                System.out.println("Họ tên: " + r.getName());
                System.out.println("Email: " + r.getEmail());
                
                System.out.println("Ngày đăng ký: " + r.getDayRequest());
                System.out.println("Trạng thái: " + r.getStatus());
                
                System.out.println("Ngày sinh: " + r.getBirth());
                System.out.println("Trường: " + r.getSchool());
                
                System.out.println("Lớp: " + r.getClassAtSchool());
                
                System.out.println("----------------------------------------------------");
            }
        }
    }
}
