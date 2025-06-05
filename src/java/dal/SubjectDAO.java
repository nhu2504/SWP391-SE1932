package dal;

import dal.DBContext;
import entity.Subject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectDAO {
    // Lấy danh sách tất cả môn học
    public List<Map<String, String>> getAllSubjects() {
        List<Map<String, String>> subjects = new ArrayList<>();
        try (Connection conn = new DBContext().connection; // Kết nối database
             PreparedStatement ps = conn.prepareStatement(
                     // Câu SQL lấy tất cả môn học
                     "SELECT SubjectId, SubjectName FROM Subjects");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { // Duyệt qua từng bản ghi
                Map<String, String> subject = new HashMap<>();
                subject.put("id", rs.getString("SubjectId")); // Lưu ID môn học
                subject.put("name", rs.getString("SubjectName")); // Lưu tên môn học
                subjects.add(subject); // Thêm vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects; // Trả về danh sách môn học
    }
    public ArrayList<Subject> getAllSubject() {
        ArrayList<Subject> subjects = new ArrayList<>();
        String sql = "SELECT SubjectID, SubjectName FROM Subjects";
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject(
                    rs.getInt("SubjectID"),
                    rs.getString("SubjectName")
                );
                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    // Lấy danh sách ảnh môn học và số lớp học
    public List<Subject> getSubjectImages(String contextPath) {
        List<Subject> subjectImages = new ArrayList<>();
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     // Câu SQL lấy ảnh và đếm số lớp học
                     "SELECT s.ImageSubject, COUNT(t.TutoringClassID) AS ClassCount "
                             + "FROM Subjects s "
                             + "LEFT JOIN TutoringClass t ON s.SubjectId = t.SubjectId "
                             + "GROUP BY s.ImageSubject");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String imageSubject = rs.getString("ImageSubject"); // Lấy đường dẫn ảnh
                int classCount = rs.getInt("ClassCount"); // Lấy số lớp học
                // Xử lý đường dẫn ảnh
                if (imageSubject != null && !imageSubject.isEmpty() && !imageSubject.startsWith("http")) {
                    if (!imageSubject.startsWith("/images/")) {
                        imageSubject = "/images/" + imageSubject.replaceFirst("^/+", "");
                    }
                    imageSubject = contextPath + imageSubject; // Thêm contextPath vào đường dẫn
                }
                subjectImages.add(new Subject(imageSubject, classCount)); // Thêm vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjectImages;
    }
    public Subject getSubjectByID(int id){
        String query = "select * from Subjects\n"
                + "where SubjectID = ?";
        try {
            Connection conn = new DBContext().connection; 
                PreparedStatement ps = conn.prepareStatement(query); 
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return new Subject(rs.getInt("SubjectID"), rs.getString("SubjectName"));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}