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
    String sql = "SELECT schoolClassID, className, schoolID, gradeID FROM SchoolClass WHERE schoolID = ?";
    try (Connection conn = new DBContext().connection;
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, schoolId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SchoolClass sc = new SchoolClass(
                    rs.getInt("SchoolClassID"),
                    rs.getString("ClassName"),
                    rs.getInt("SchoolID"),
                    rs.getInt("GradeID")
                );
                list.add(sc);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
    //test lấy dữ liệu
    public static void main(String[] args) {
//        SchoolClassDAO sd = new SchoolClassDAO();
//                int sID = 2; 
//                SchoolClass s = sd.getSchoolClassByID(sID);
//    
//    if (s != null) {
//        System.out.println("Lớp tìm thấy: " + s.toString());
//    } else {
//        System.out.println("Không tìm thấy lớp với ID: " + sID);
//    }

        SchoolClassDAO dao = new SchoolClassDAO();

        int schoolId = 1; // Thay bằng ID trường học có thật trong cơ sở dữ liệu

        List<SchoolClass> classes = dao.getAllClassesBySchoolId(schoolId);

        if (classes.isEmpty()) {
            System.out.println("Không có lớp nào thuộc trường có ID = " + schoolId);
        } else {
            System.out.println("Danh sách các lớp thuộc trường ID = " + schoolId + ":");
            for (SchoolClass sc : classes) {
                System.out.println(" - Lớp ID: " + sc.getSchoolClassID()
                        + ", Tên lớp: " + sc.getClassName()
                        + ", GradeID: " + sc.getGradeID());
            }
        }
    }
}
