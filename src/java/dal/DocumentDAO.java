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
        loginDAO ld = new loginDAO();
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
                    
                    ld.getUserByID(rs.getInt("UploadedBy")),
                        rs.getDate("UploadDate"),
                    sd.getSubjectByID(rs.getInt("SubjectID"))
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
                     // Câu SQL lấy thông tin tài liệu
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
}