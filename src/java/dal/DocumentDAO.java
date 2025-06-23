package dal;

import entity.Document;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DocumentDAO - Quản lý truy xuất dữ liệu tài liệu từ bảng Document
 *
 * - Lấy danh sách tài liệu theo khối và môn học.
 * - Sử dụng trong trang chủ để hiển thị tài liệu lọc theo yêu cầu.
 *
 * Ngày tạo: 23/06/2025
 * Người viết: Van Nhu
 */
public class DocumentDAO {

    /**
     * Lấy danh sách tài liệu theo khối và môn học.
     * Nếu gradeId hoặc subjectId <= 0 thì bỏ qua điều kiện đó.
     *
     * @param gradeId   ID của khối lớp (có thể = 0 nếu không lọc)
     * @param subjectId ID của môn học (có thể = 0 nếu không lọc)
     * @return Danh sách tài liệu thỏa điều kiện
     */
    public List<Document> getDocumentsByGradeAndSubject(int gradeId, int subjectId) {
        List<Document> docs = new ArrayList<>();

        // Câu SQL động: WHERE 1=1 để dễ nối thêm điều kiện
        StringBuilder sql = new StringBuilder(
                "SELECT DocumentId, Title, Descrip, UploadedBy, UploadDate, SubjectId, GradeID " +
                "FROM Document WHERE 1=1"
        );

        // Thêm điều kiện nếu có truyền khối lớp
        if (gradeId > 0) {
            sql.append(" AND GradeID=?");
        }

        // Thêm điều kiện nếu có truyền môn học
        if (subjectId > 0) {
            sql.append(" AND SubjectID=?");
        }

        try (
            Connection conn = new DBContext().connection; // Kết nối đến DB
            PreparedStatement ps = conn.prepareStatement(sql.toString()) // Tạo PreparedStatement từ câu truy vấn
        ) {
            int idx = 1;

            // Gán tham số theo thứ tự
            if (gradeId > 0) ps.setInt(idx++, gradeId);
            if (subjectId > 0) ps.setInt(idx++, subjectId);

            try (ResultSet rs = ps.executeQuery()) {
                // Lặp qua kết quả truy vấn
                while (rs.next()) {
                    Document doc = new Document();
                    doc.setDocumentId(rs.getInt("DocumentId"));
                    doc.setTitle(rs.getString("Title"));
                    doc.setDescription(rs.getString("Descrip"));
                    doc.setUploadedBy(rs.getInt("UploadedBy"));
                    doc.setUploadDate(rs.getTimestamp("UploadDate"));
                    doc.setSubjectId(rs.getInt("SubjectId"));
                    doc.setGradeId(rs.getInt("GradeID"));
                    docs.add(doc); // Thêm vào danh sách kết quả
                }
            }

        } catch (SQLException e) {
            // In lỗi nếu có
            e.printStackTrace();
        }

        return docs;
    }
}
