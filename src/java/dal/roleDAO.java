/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.roles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DO NGOC ANH HE180661
 */
public class roleDAO {

    public roles getRoleByID(int id) {
        String query = "select * from roles\n"
                + "where roleID = ?";
        try {
            Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(query); 
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return new roles(rs.getInt(1), 
                            rs.getString(2));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<roles> getAllRoles(){
        List<roles> list = new ArrayList<>();
        String query = "select * from roles";
        
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                roles r = new roles();
                r.setId(rs.getInt("roleID"));
                r.setRole(rs.getString("roleName"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
        roleDAO r = new roleDAO();
        List<roles> rolesList = r.getAllRoles();
for (roles role : rolesList) {
    System.out.println(role.toString());
}
       
//        int roleID = 2; 
//    roles role = r.getRoleByID(roleID);
//    if (role != null) {
//        System.out.println("Sản phẩm tìm thấy: " + role.toString());
//    } else {
//        System.out.println("Không tìm thấy sản phẩm với ID: " + roleID);
//    }
    }
}
