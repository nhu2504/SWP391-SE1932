/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author NGOC ANH
 */
public class ClassGroup_StudentDAO {

    public List<User> getStudentsByClassGroupId(int classGroupId) {
        List<User> students = new ArrayList<>();
        String sql = """
                     SELECT u.UserID, u.FullName, u.email, u.phone, u.avatar,
                            s.SchoolName,
                            sc.className AS SchoolClassName,
                            u.ParentEmail, u.ParentPhone
                     FROM ClassGroup_Student cgs
                     JOIN [User] u ON cgs.StudentID = u.UserID
                     LEFT JOIN School s ON u.SchoolID = s.SchoolID
                     LEFT JOIN TeacherClass tc ON u.UserID = tc.UserID
                     LEFT JOIN SchoolClass sc ON tc.SchoolClassID = sc.SchoolClassID
                     WHERE cgs.ClassGroupID = ? AND cgs.IsActive = 1""";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classGroupId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User student = new User();
                student.setId(rs.getInt("UserID"));
                student.setName(rs.getString("FullName"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setAvatar(rs.getString("avatar"));
                student.setSchoolName(rs.getString("SchoolName"));
                student.setSchoolClassName(rs.getString("SchoolClassName"));
                student.setParentEmail(rs.getString("ParentEmail"));
                student.setParentPhone(rs.getString("ParentPhone"));
                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public static void main(String[] args) {
        // Giả sử bạn đã có class DAO tên là ClassGroupDAO
        ClassGroup_StudentDAO dao = new ClassGroup_StudentDAO();

        // Giả sử classGroupId = 1 là lớp học có thật trong CSDL
        int classGroupId = 1;

        List<User> students = dao.getStudentsByClassGroupId(classGroupId);

        if (students.isEmpty()) {
            System.out.println("Không có học sinh nào trong lớp có ID: " + classGroupId);
        } else {
            System.out.println("Danh sách học sinh trong lớp ID = " + classGroupId + ":");
            for (User student : students) {
                System.out.println("=================================");
                System.out.println("ID: " + student.getId());
                System.out.println("Tên: " + student.getName());
                System.out.println("Email: " + student.getEmail());
                System.out.println("SĐT: " + student.getPhone());
                System.out.println("Avatar: " + student.getAvatar());
            }
        }
    }

}
