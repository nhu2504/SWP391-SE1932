/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.roles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        }
        return null;
    }
    public static void main(String[] args) {
        roleDAO r = new roleDAO();
        
        int roleID = 2; 
    roles role = r.getRoleByID(roleID);
    if (role != null) {
        System.out.println("Sản phẩm tìm thấy: " + role.toString());
    } else {
        System.out.println("Không tìm thấy sản phẩm với ID: " + roleID);
    }
    }
}
