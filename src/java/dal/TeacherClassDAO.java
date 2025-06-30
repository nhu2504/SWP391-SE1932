/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.SchoolClass;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author NGOC ANH
 */
public class TeacherClassDAO {

    public List<SchoolClass> getSchoolClassesByTeacherId(int teacherId) {
        List<SchoolClass> list = new ArrayList<>();
        String sql = """
                     SELECT sc.* FROM TeacherClass tc 
                                         JOIN SchoolClass sc ON tc.schoolClassId = sc.SchoolClassID 
                                         WHERE tc.UserID = ?""";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SchoolClass sc = new SchoolClass();
                sc.setSchoolClassID(rs.getInt("SchoolClassID"));
                sc.setClassName(rs.getString("ClassName"));
                sc.setSchoolID(rs.getInt("SchoolID"));
                sc.setGradeID(rs.getInt("GradeID"));
                list.add(sc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateSchoolClassDAO(int userId, List<Integer> classIds) {
        String deleteSql = "DELETE FROM TeacherClass WHERE UserID = ?";
        String insertSql = "INSERT INTO TeacherClass (UserID, SchoolClassID) VALUES (?, ?)";
        Connection conn = null;
        try {
            conn = new DBContext().connection;
            conn.setAutoCommit(false);

            // Xóa các lớp cũ
            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setInt(1, userId);
                ps.executeUpdate();
            }

            // Thêm các lớp mới (nếu có)
            if (classIds != null) {
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    for (Integer classId : classIds) {
                        ps.setInt(1, userId);
                        ps.setInt(2, classId);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
//        TeacherClassDAO dao = new TeacherClassDAO();
//
//        int testTeacherId = 2; // Thay bằng ID của giáo viên bạn có trong DB
//
//        List<SchoolClass> classes = dao.getSchoolClassesByTeacherId(testTeacherId);
//
//        if (classes.isEmpty()) {
//            System.out.println("Không tìm thấy lớp nào cho giáo viên có ID = " + testTeacherId);
//        } else {
//            System.out.println("Các lớp của giáo viên có ID = " + testTeacherId + ":");
//            for (SchoolClass sc : classes) {
//                System.out.println(" - Lớp: " + sc.getClassName()
//                        + ", SchoolClassID = " + sc.getSchoolClassID()
//                        + ", SchoolID = " + sc.getSchoolID());
//            }
//        }
        TeacherClassDAO dao = new TeacherClassDAO();

        int teacherId = 2; // Thay bằng ID của giáo viên trong DB
        List<Integer> newClassIds = Arrays.asList(1, 2, 12); // Danh sách lớp mới

        boolean result = dao.updateSchoolClassDAO(teacherId, newClassIds);

        if (result) {
            System.out.println("Cập nhật danh sách lớp học cho giáo viên " + teacherId + " thành công.");
        } else {
            System.out.println("Cập nhật thất bại.");
        }
    }
}