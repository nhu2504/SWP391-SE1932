package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity.Document;

/**
 *
 */
public class DocumentDAO {
    private static final Logger LOGGER = Logger.getLogger(DocumentDAO.class.getName());
    private DBContext dbContext;

    public DocumentDAO() {
        dbContext = new DBContext();
    }
    public ArrayList<Document> getDocumentsByUserID(int userID) throws SQLException {
        ArrayList<Document> documents = new ArrayList<>();
        String sql = "SELECT d.* FROM Document d " +
                     "JOIN TutoringRegistration tr ON d.ClassID = tr.TutoringClassID " +
                     "WHERE tr.UserID = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dbContext.connection;
            if (conn == null) {
                throw new SQLException("Không thể lấy kết nối cơ sở dữ liệu");
            }
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Document doc = new Document(
                    rs.getInt("DocumentID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("FilePath"),
                    rs.getString("ExternalLink"),
                    rs.getInt("UploadedBy"),
                    rs.getString("UploadDate"),
                    rs.getInt("ClassID")
                );
                documents.add(doc);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy tài liệu cho userID: " + userID, e);
            throw e;
        } finally {
            closeResources(rs, stmt, conn);
        }
        return documents;
    }
    private void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Không thể đóng ResultSet", e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Không thể đóng PreparedStatement", e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Không thể đóng Connection", e);
            }
        }
    }
}