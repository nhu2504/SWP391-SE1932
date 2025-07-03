/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Roles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DO NGOC ANH HE180661
 */
public class RoleDAO {

    public Roles getRoleByID(int id) {
        String query = "select * from roles\n"
                + "where roleID = ?";
        try {
            Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(query); 
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return new Roles(rs.getInt(1), 
                            rs.getString(2));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Roles> getAllRoles(){
        List<Roles> list = new ArrayList<>();
        String query = "select * from roles";
        
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Roles r = new Roles();
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
        RoleDAO r = new RoleDAO();
        List<Roles> rolesList = r.getAllRoles();
for (Roles role : rolesList) {
    System.out.println(role.toString());
}
       
//        int roleID = 2; 
//    Roles role = r.getRoleByID(roleID);
//    if (role != null) {
//        System.out.println("Sản phẩm tìm thấy: " + role.toString());
//    } else {
//        System.out.println("Không tìm thấy sản phẩm với ID: " + roleID);
//    }
    }
}
