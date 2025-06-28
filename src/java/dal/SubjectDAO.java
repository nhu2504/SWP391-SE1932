package dal;

import dal.DBContext;
import entity.Subject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Ngày tạo: 23/06/2025 Người viết: Van Nhu
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

    public List<Subject> getSubjectsByTeacherId(int teacherId) {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT s.* \n"
                + "                   FROM TeacherSubjects ts \n"
                + "                   JOIN Subjects s ON ts.SubjectID = s.SubjectID \n"
                + "                   WHERE ts.UserID = ?";
        try (Connection conn = new DBContext().connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Subject sub = new Subject();
                sub.setSubjectId(rs.getInt("SubjectID"));
                sub.setSubjectName(rs.getString("SubjectName"));
                sub.setImageSubject(rs.getString("ImageSubject"));
                subjects.add(sub);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public boolean updateSubjectOfTeacherDAO(int teacherId, List<Integer> subjectIds) {
        String deleteSql = "DELETE FROM TeacherSubjects WHERE UserID = ?";
        String insertSql = "INSERT INTO TeacherSubjects (UserID, SubjectID) VALUES (?, ?)";
        Connection conn = null;
        try {
            conn = new DBContext().connection;
            conn.setAutoCommit(false);

            // Xóa hết chuyên môn cũ
            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setInt(1, teacherId);
                ps.executeUpdate();
            }

            // Thêm các chuyên môn mới (nếu có)
            if (subjectIds != null) {
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    for (Integer subjectId : subjectIds) {
                        ps.setInt(1, teacherId);
                        ps.setInt(2, subjectId);
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
        SubjectDAO dao = new SubjectDAO();
//
//        int teacherId = 2; // Thay bằng ID giáo viên đang có trong DB của bạn
//
//        List<Subject> subjectList = dao.getSubjectsByTeacherId(teacherId);
//
//        if (subjectList.isEmpty()) {
//            System.out.println("Không có môn học nào cho giáo viên có ID = " + teacherId);
//        } else {
//            System.out.println("Danh sách môn học của giáo viên ID = " + teacherId + ":");
//            for (Subject sub : subjectList) {
//                System.out.println(" - Mã môn: " + sub.getSubjectId()
//                        + ", Tên môn: " + sub.getSubjectName()
//                        + ", Ảnh đại diện: " + sub.getImageSubject());
//            }
//        }

        

//        int teacherId = 2; // Thay bằng ID của giáo viên đã có trong DB
//        List<Integer> subjectIds = Arrays.asList(1, 8); // Danh sách SubjectID cần gán mới
//
//        boolean result = dao.updateSubjectOfTeacherDAO(teacherId, subjectIds);
//
//        if (result) {
//            System.out.println("Cập nhật chuyên môn cho giáo viên ID = " + teacherId + " thành công.");
//        } else {
//            System.out.println("Cập nhật chuyên môn thất bại.");
//        }

        List<Subject> subjects = dao.getAllSubjects();

        if (subjects.isEmpty()) {
            System.out.println("Không có môn học nào trong hệ thống.");
        } else {
            System.out.println("Danh sách môn học:");
            for (Subject subject : subjects) {
                System.out.println("ID: " + subject.getSubjectId() + " | Tên: " + subject.getSubjectName());
            }
        }
    }
}
