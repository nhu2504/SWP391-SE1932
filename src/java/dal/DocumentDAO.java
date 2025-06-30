package dal;

import entity.Document;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ngày update: 30/06/2025  
 * Người viết: Văn Thị Như
 * Mục đích: Truy xuất dữ liệu tài liệu học tập từ bảng Document theo lớp (Grade) và môn học (Subject)
 */
public class DocumentDAO {

    /**
     *  Lấy danh sách tài liệu theo lớp và môn học
     *
     * @param gradeId   ID của khối lớp (GradeID), nếu <= 0 thì không lọc theo lớp
     * @param subjectId ID của môn học (SubjectID), nếu <= 0 thì không lọc theo môn
     * @return Danh sách tài liệu thỏa mãn điều kiện tìm kiếm
     */
    public List<Document> getDocumentsByGradeAndSubject(int gradeId, int subjectId) {
        List<Document> docs = new ArrayList<>();

        // Xây dựng câu lệnh SQL linh hoạt tùy theo bộ lọc lớp và môn học
        StringBuilder sql = new StringBuilder("""
            SELECT DocumentId, Title, Descrip, UploadedBy, UploadDate, SubjectId, GradeID
            FROM Document
            WHERE 1=1
        """);

        // Thêm điều kiện nếu có lọc theo lớp
        if (gradeId > 0) {
            sql.append(" AND GradeID = ?");
        }

        // Thêm điều kiện nếu có lọc theo môn học
        if (subjectId > 0) {
            sql.append(" AND SubjectID = ?");
        }

        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Gán tham số cho câu lệnh truy vấn theo thứ tự
            int idx = 1;
            if (gradeId > 0) ps.setInt(idx++, gradeId);
            if (subjectId > 0) ps.setInt(idx++, subjectId);

            // Thực thi truy vấn và đọc kết quả
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Document doc = new Document();
                    doc.setDocumentId(rs.getInt("DocumentId"));     // ID tài liệu
                    doc.setTitle(rs.getString("Title"));            // Tiêu đề
                    doc.setDescription(rs.getString("Descrip"));    // Mô tả
                    doc.setUploadedBy(rs.getInt("UploadedBy"));     // Người tải lên
                    doc.setUploadDate(rs.getTimestamp("UploadDate")); // Ngày tải lên
                    doc.setSubjectId(rs.getInt("SubjectId"));       // ID môn học
                    doc.setGradeId(rs.getInt("GradeID"));           // ID lớp
                    docs.add(doc);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log nếu có lỗi
        }

        return docs; // Trả về danh sách kết quả
    }
}
