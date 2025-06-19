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
        GradeDAO gd = new GradeDAO();
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
                            rs.getInt(3), 
                            rs.getInt(4));
                    
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        SchoolClassDAO sd = new SchoolClassDAO();
                int sID = 2; 
                SchoolClass s = sd.getSchoolClassByID(sID);
    
    if (s != null) {
        System.out.println("Sáº£n pháº©m tĂ¬m tháº¥y: " + s.toString());
    } else {
        System.out.println("KhĂ´ng tĂ¬m tháº¥y sáº£n pháº©m vá»›i ID: " + sID);
    }
    }
}
