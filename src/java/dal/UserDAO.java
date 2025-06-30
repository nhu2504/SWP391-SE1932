/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author DO NGOC ANH HE180661
 */
public class UserDAO {

    public User login(String email, String password) {
        String query = "select * from [User]\n"
                + "where email = ?\n"
                + "and pass = ?";
        
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt("UserID"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getDate("BirthDate"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("pass"),
                        rs.getString("avatar"),
                        rs.getInt("onlineStatus"),
                        rs.getDate("created_at"),
                        rs.getString("Certi"),
                        rs.getString("Descrip"),
                        rs.getInt("SchoolID"),
                        rs.getInt("SchoolClassID"),
                        rs.getInt("roleID"),
                        rs.getString("ParentEmail"),
                        rs.getString("ParentPhone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByID(int id) {
        String query = "  select * from [User]\n"
                + "  where UserID = ?";
        
        try {
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("UserID"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getDate("BirthDate"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("pass"),
                        rs.getString("avatar"),
                        rs.getInt("onlineStatus"),
                        rs.getDate("created_at"),
                        rs.getString("Certi"),
                        rs.getString("Descrip"),
                        rs.getInt("SchoolID"),
                        rs.getInt("SchoolClassID"),
                        rs.getInt("roleID"),
                        rs.getString("ParentEmail"),
                        rs.getString("ParentPhone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public User getUserByEmail(String email) {
    String query = "select * from [User] where email = ?";
   
    try {
        Connection conn = new DBContext().connection;
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new User(rs.getInt("UserID"),
                    rs.getString("FullName"),
                    rs.getString("Gender"),
                    rs.getDate("BirthDate"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("pass"),
                    rs.getString("avatar"),
                    rs.getInt("onlineStatus"),
                    rs.getDate("created_at"),
                    rs.getString("Certi"),
                    rs.getString("Descrip"),
                    rs.getInt("SchoolID"),
                    rs.getInt("SchoolClassID"),
                   rs.getInt("roleID"),
                    rs.getString("ParentEmail"),
                    rs.getString("ParentPhone"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
public static void main(String[] args) {
        UserDAO r = new UserDAO();

       
        String email = "anhdnhe180661@fpt.edu.vn"; 
    User role = r.getUserByEmail(email);
    if (role != null) {
        System.out.println("Người dùng tìm thấy: " + role.toString());
    } else {
        System.out.println("Không tìm thấy người dùng với email: " + email);
    }
    }

}
