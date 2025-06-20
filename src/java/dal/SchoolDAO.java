/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.School;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DO NGOC ANH HE180661
 */
//class chứa các hàm tương tác với bảng school
public class SchoolDAO {
    //lấy ra tất cả các trường học đã liên kết với trung tâm
    public List<School> getAllSchools() {
        List<School> schools = new ArrayList<>();
        String sql = "SELECT * FROM School";
        //kết nối sql
        try (Connection conn = new DBContext().connection;
                //chạy câu lệnh sql phía trên
             PreparedStatement ps = conn.prepareStatement(sql);
                //nhận kết quả trả về 
             ResultSet rs = ps.executeQuery()) {
            //duyệt qua từng dòng, tạo đối tượng school và gán giá trị tương ứng từ db
            while (rs.next()) {
                School s = new School();
                s.setSchoolID(rs.getInt("SchoolID"));
                s.setName(rs.getString("SchoolName"));
                s.setAddress(rs.getString("AddressSchool"));
                //thêm vào list school
                schools.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi "+e.getMessage());
        }
        return schools;
    }
    //lấy ra trường học theo id
    public School getSchoolByID(int id) {
        String query = """
                       select * from School
                         where SchoolID = ?""";
        try {
            //mở kết nối,tạo truy vấn
            Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(query); 
                //gán id vào tham số thứ nhất
                ps.setInt(1, id);
                //truy vấn và lấy kết quả
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return new School(rs.getInt(1), 
                            rs.getString(2));
                }
        } catch (SQLException e) {
            System.out.println("Lỗi "+e.getMessage());
        }
        return null;
    } 
    //lấy ra tên trường học theo id
    public String getSchoolNameById(int schoolID) {
        String schoolName = "";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT SchoolName FROM School WHERE SchoolID = ?")) {
            ps.setInt(1, schoolID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                schoolName = rs.getString("SchoolName");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi "+e.getMessage());
        }
        return schoolName;
    }
    //test xem đã lấy được dữ liệu từ db chưa
    public static void main(String[] args) {
        SchoolDAO sd = new SchoolDAO();
                int sID = 2; 
                School s = sd.getSchoolByID(sID);
    
    if (s != null) {
        System.out.println("Sản phẩm tìm thấy: " + s.toString());
    } else {
        System.out.println("Không tìm thấy sản phẩm với ID: " + sID);
    }
    }
}
