/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.account;
import entity.roles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author DO NGOC ANH HE180661
 */
public class loginDAO {

    public account login(String email, String password, int roleid) {
        String query = "select * from [User]\n"
                + "where email = ?\n"
                + "and pass = ? \n"
                + "and roleID = ?";
        roleDAO rd = new roleDAO();
        SchoolDAO sd = new SchoolDAO();
        SchoolClassDAO scd = new SchoolClassDAO();
        try {
              Connection conn = new DBContext().connection;
                PreparedStatement ps = conn.prepareStatement(query); 
                ps.setString(1, email);
                ps.setString(2, password);
                ps.setInt(3, roleid);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    return new account(rs.getInt("UserID"),
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
                            sd.getSchoolByID(rs.getInt("SchoolID")),
                            scd.getSchoolClassByID(rs.getInt("SchoolClassID")),
                            rd.getRoleByID(rs.getInt("roleID")), 
                            rs.getString("ParentEmail"),
                            rs.getString("ParentPhone"));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
