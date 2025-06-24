package dal;

import entity.School;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 *
 * Ngày tạo: 23/06/2025  
 * Người viết: Van Nhu
 */
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
}
