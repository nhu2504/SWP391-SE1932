package dal;

import dal.DBContext;
import entity.Subject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ngày update: 30/06/2025  
 * Người viết: Văn Thị Như
 * 
 * Mô tả: DAO xử lý truy vấn dữ liệu liên quan đến môn học (Subjects)
 */
public class SubjectDAO {

    /**
     *  Lấy toàn bộ danh sách môn học từ bảng Subjects
     *
     * @return List<Subject> danh sách các môn học
     */
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT SubjectId, SubjectName FROM Subjects";
        try (
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("SubjectId"));
                subject.setSubjectName(rs.getString("SubjectName"));
                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    /**
     *  Lấy danh sách môn học kèm theo số lượng lớp đang mở ứng với từng môn
     *
     * Truy vấn liên kết:
     * - Bảng Subjects (môn học)
     * - Bảng TutoringClass (lớp học) → đếm số lớp theo từng môn
     *
     * Dùng LEFT JOIN để vẫn hiển thị môn học nếu chưa có lớp nào
     *
     * @return List<Subject> trong đó mỗi Subject có thêm thuộc tính classCount
     */
    public List<Subject> getSubjectsWithClassCount() {
        List<Subject> list = new ArrayList<>();
        String sql = """
            SELECT s.SubjectId, s.SubjectName, s.ImageSubject, COUNT(c.TutoringClassID) AS classCount
            FROM Subjects s
            LEFT JOIN TutoringClass c ON s.SubjectId = c.SubjectID
            GROUP BY s.SubjectId, s.SubjectName, s.ImageSubject
        """;
        try (
            Connection conn = new DBContext().connection;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("SubjectId"));
                subject.setSubjectName(rs.getString("SubjectName"));
                subject.setImageSubject(rs.getString("ImageSubject"));
                subject.setClassCount(rs.getInt("classCount"));
                list.add(subject);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
