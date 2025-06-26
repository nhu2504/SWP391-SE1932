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
    public List<School> getAllSchools() {
        List<School> schools = new ArrayList<>();
        String sql = "SELECT SchoolID, SchoolName, AddressSchool FROM School";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                School s = new School();
                s.setSchoolID(rs.getInt("SchoolID"));
                s.setName(rs.getString("SchoolName"));
                s.setAddress(rs.getString("AddressSchool"));
                schools.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schools;
    }
    
//    public School getSchoolByID(int id) {
//        String query = "select * from School\n"
//                + "  where SchoolID = ?";
//        try {
//            Connection conn = new DBContext().connection; 
//                PreparedStatement ps = conn.prepareStatement(query); 
//                ps.setInt(1, id);
//                ResultSet rs = ps.executeQuery();
//                if(rs.next()){
//                    return new School(rs.getInt(1), 
//                            rs.getString(2));
//                }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    } 
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
            e.printStackTrace();
        }
        return schoolName;
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
