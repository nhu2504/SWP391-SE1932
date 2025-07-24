/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;


import entity.SchoolClass;
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
public class SchoolClassDAO {
    //lấy ra lớp của trường học đã liên kết theo id
    public SchoolClass getSchoolClassByID(int id) {
        //lấy ra 1 lớp học trong trường
        String query = """
                       select * from SchoolClass
                         where SchoolClassID = ?""";
        //tạo kết nối, chuẩn bị chạy câu lệnh sql
        try {
            Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(query); 
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                //nếu có kết quả được trả về, dùng constructor để tạo đối tượng
                if(rs.next()){
                    return new SchoolClass(rs.getInt(1), 
                            rs.getString(2), 
                            rs.getInt(3), 
                            rs.getInt(4));
                    
                }
        } catch (SQLException e) {
            System.out.println("Lỗi "+e.getMessage());
        }
        return null;
    }
    public String getSchoolClassNameById(int schoolClassId) {
        String ClassName = "";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                 "select ClassName from SchoolClass where SchoolClassID = ?")) {
            ps.setInt(1, schoolClassId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ClassName = rs.getString("ClassName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ClassName;
    }
    
    
    public List<SchoolClass> getAllClassesBySchoolId(int schoolId) {
    List<SchoolClass> list = new ArrayList<>();
    String sql = "SELECT schoolClassID, className, schoolID FROM SchoolClass WHERE schoolID = ?";
    try (Connection conn = new DBContext().connection;
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, schoolId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SchoolClass sc = new SchoolClass(
                    rs.getInt("SchoolClassID"),
                    rs.getString("ClassName"),
                    rs.getInt("SchoolID")
                   
                );
                list.add(sc);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
    public SchoolClass getSchoolByUserId(int userId) {
    SchoolClass sc = null;
    String sql = "SELECT s.ClassName " +
                 "FROM [User] u " +
                 "JOIN School s ON u.SchoolClassID = s.SchoolClassID " +
                 "WHERE u.UserID = ?";

    try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            sc = new SchoolClass();
            
            sc.setClassName(rs.getString("SchoolName"));
            
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return sc;
}
    //test lấy dữ liệu
    public static void main(String[] args) {
SchoolClassDAO dao = new SchoolClassDAO();
        int schoolId = 1; // thay đổi ID theo dữ liệu thật trong DB

        List<SchoolClass> classes = dao.getAllClassesBySchoolId(schoolId);

        for (SchoolClass sc : classes) {
            System.out.println(sc);
        }

        if (classes.isEmpty()) {
            System.out.println("Không có lớp nào cho schoolID = " + schoolId);
        }
    }
    
    // Như
    public List<SchoolClass> getAllSchoolClasses() {
    List<SchoolClass> list = new ArrayList<>();
    String sql = "SELECT schoolClassID, className, schoolID, gradeID FROM SchoolClass";
    try (Connection conn = new DBContext().connection;
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            SchoolClass sc = new SchoolClass(
                rs.getInt("SchoolClassID"),
                rs.getString("ClassName"),
                rs.getInt("SchoolID"),
                rs.getInt("GradeID")
            );
            list.add(sc);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}
