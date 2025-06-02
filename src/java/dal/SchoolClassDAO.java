/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;


import entity.SchoolClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author NGOC ANH
 */
public class SchoolClassDAO {

    public SchoolClass getSchoolClassByID(int id) {
        SchoolDAO sd = new SchoolDAO();
        String query = "select * from SchoolClass\n"
                + "  where SchoolClassID = ?";
        try {
            Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(query); 
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return new SchoolClass(rs.getInt(1), 
                            rs.getString(2), 
                            sd.getSchoolByID(rs.getInt(3)));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
