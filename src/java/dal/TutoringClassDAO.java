package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity.TutoringClass;

/**
 *
 */
public class TutoringClassDAO {
    private static final Logger LOGGER = Logger.getLogger(TutoringClassDAO.class.getName());
    private DBContext dbContext;

    public TutoringClassDAO() {
        dbContext = new DBContext();
    }
    public ArrayList<TutoringClass> getClassesByUserID(int userID) throws SQLException {
        ArrayList<TutoringClass> classes = new ArrayList<>();
        String sql = "SELECT tc.* FROM TutoringClass tc " +
                     "JOIN TutoringRegistration tr ON tc.TutoringClassID = tr.TutoringClassID " +
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
                TutoringClass tc = new TutoringClass(
                    rs.getInt("TutoringClassID"),
                    rs.getString("ClassName"),
                    rs.getInt("SubjectID"),
                    rs.getInt("TeacherID"),
                    rs.getString("StartDate"),
                    rs.getString("EndDate"),
                    rs.getString("Schedule")
                );
                classes.add(tc);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy lớp học cho userID: " + userID, e);
            throw e;
        } finally {
            closeResources(rs, stmt, conn);
        }
        return classes;
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