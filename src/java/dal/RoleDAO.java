/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Roles;
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
public class RoleDAO {
    //lấy ra vai trò theo id
    public Roles getRoleByID(int id) {
        //câu lệnh sql láya ra vai trò
        String query = """
                       select * from roles
                       where roleID = ?""";
        //mở kết nối
        try {
            Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(query); 
                ps.setInt(1, id);
                //thực hiện truy vấn
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return new Roles(rs.getInt(1), 
                            rs.getString(2));
                }
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }
    public String getRoleNameByID(int id){
        String sql = "select roleName from roles where roleID = ?";
        try {
            Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(sql); 
                ps.setInt(1, id);
                //thực hiện truy vấn
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return rs.getString(1);
                }
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }
    //lấy ra tất cả các vai trò
    public List<Roles> getAllRoles(){
        //khởi tạo list lưu trữ các role được lấy ra từ db
        List<Roles> list = new ArrayList<>();
        //câu lệnh thực thi
        String query = "select * from roles";
        //mở kết nối
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            //lặp qua kết quả trả về từ bảng
            while(rs.next()){
                Roles r = new Roles();
                r.setId(rs.getInt("roleID"));
                r.setRole(rs.getString("roleName"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return list;
    }
    public static void main(String[] args) {
        RoleDAO r = new RoleDAO();
//        List<Roles> rolesList = r.getAllRoles();
//for (Roles role : rolesList) {
//    System.out.println(role.toString());
//}
       
        int roleID = 2; 
    String role = r.getRoleNameByID(roleID);
    if (role != null) {
        System.out.println("Sản phẩm tìm thấy: " + role.toString());
    } else {
        System.out.println("Không tìm thấy sản phẩm với ID: " + roleID);
    }
    }
}
