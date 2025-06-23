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
 * Ngày tạo: 23/06/2025  
 * Người viết: Van Nhu
 */
public class SubjectDAO {

    // Lấy danh sách tất cả môn học
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(
                "SELECT SubjectId, SubjectName FROM Subjects"); ResultSet rs = ps.executeQuery()) {
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
// Lấy danh sách môn học và số lớp tương ứng
    public List<Subject> getSubjectsWithClassCount() {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT s.SubjectId, s.SubjectName, s.ImageSubject, COUNT(c.TutoringClassID) AS classCount "
                + "FROM Subjects s "
                + "LEFT JOIN TutoringClass c ON s.SubjectId = c.SubjectID "
                + "GROUP BY s.SubjectId, s.SubjectName, s.ImageSubject";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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
