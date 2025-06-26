package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Document;

public class DocumentDAO {
    private DBContext dbContext;

    public DocumentDAO() {
        dbContext = DBContext.getInstance();
    }

    public ArrayList<Document> getDocumentsByUserID(int userID) {
        ArrayList<Document> documents = new ArrayList<>();
        String sql = """
            SELECT d.DocumentID, d.Title, d.Descrip, d.FilePath, d.UploadedBy, d.SubjectID 
            FROM Document d 
            JOIN TutoringRegistration tr ON d.SubjectID = (
                SELECT SubjectID FROM TutoringClass WHERE TutoringClassID = tr.TutoringClassID
            ) 
            WHERE tr.UserID = ?
        """;
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Document doc = new Document(
                    rs.getInt("DocumentID"),
                    rs.getString("Title"),
                    rs.getString("Descrip"),
                    rs.getString("FilePath"),
                    rs.getInt("UploadedBy"),
                    rs.getInt("SubjectID")
                );
                documents.add(doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }
}