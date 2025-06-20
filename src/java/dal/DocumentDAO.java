package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Document;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentDAO {
    public ArrayList<Document> getDocumentsByUserID(int userID) {
        ArrayList<Document> documents = new ArrayList<>();
        String sql = """
            SELECT d.DocumentID, d.Title, d.Descrip,  d.UploadedBy,d.UploadDate, d.SubjectID 
            FROM Document d 
            JOIN TutoringRegistration tr ON d.SubjectID = (
                SELECT SubjectID FROM TutoringClass WHERE TutoringClassID = tr.TutoringClassID
            ) 
            WHERE tr.UserID = ?
        """;
        UserDAO ld = new UserDAO();
        SubjectDAO sd = new SubjectDAO();
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Document doc = new Document(
                    rs.getInt("DocumentID"),
                    rs.getString("Title"),
                    rs.getString("Descrip"),
                    
                   rs.getInt("UploadedBy"),
                        rs.getDate("UploadDate"),
                    rs.getInt("SubjectID")
                );
                documents.add(doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }
    public Map<String, List<Map<String, String>>> getAllDocuments() {
        Map<String, List<Map<String, String>>> documents = new HashMap<>();
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(
                     // CĂ¢u SQL láº¥y thĂ´ng tin tĂ i liá»‡u
                     "SELECT DocumentId, Title, Descrip, SubjectId FROM Document");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String subjectId = rs.getString("SubjectId");
                Map<String, String> doc = new HashMap<>();
                doc.put("id", rs.getString("DocumentId"));
                doc.put("name", rs.getString("Title"));
                doc.put("url", rs.getString("Descrip"));
                documents.computeIfAbsent(subjectId, k -> new ArrayList<>()).add(doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }
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