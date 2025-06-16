package dal;

import entity.Document;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//Văn Thị NHư - HE181329
public class DocumentDAO {
    public List<Document> getDocumentsByGradeAndSubject(int gradeId, int subjectId) {
    List<Document> docs = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT DocumentId, Title, Descrip, UploadedBy, UploadDate, SubjectId, GradeID FROM Document WHERE 1=1");
    if (gradeId > 0) {
        sql.append(" AND GradeID=?");
    }
    if (subjectId > 0) {
        sql.append(" AND SubjectID=?");
    }
    try (Connection conn = new DBContext().connection;
         PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        int idx = 1;
        if (gradeId > 0) ps.setInt(idx++, gradeId);
        if (subjectId > 0) ps.setInt(idx++, subjectId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Document doc = new Document();
                doc.setDocumentId(rs.getInt("DocumentId"));
                doc.setTitle(rs.getString("Title"));
                doc.setDescription(rs.getString("Descrip"));
                doc.setUploadedBy(rs.getInt("UploadedBy"));
                doc.setUploadDate(rs.getTimestamp("UploadDate"));
                doc.setSubjectId(rs.getInt("SubjectId"));
                doc.setGradeId(rs.getInt("GradeID"));
                docs.add(doc);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return docs;
}
}