package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity.Grade;

/**
 *
 */
public class GradeDAO {
    private static final Logger LOGGER = Logger.getLogger(GradeDAO.class.getName());
    private DBContext dbContext;

    public GradeDAO() {
         dbContext = DBContext.getInstance();
    }
    public ArrayList<Grade> getGradesByUserID(int userID) throws SQLException {
        ArrayList<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM Grade WHERE UserID = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dbContext.getConnection();
            if (conn == null) {
                throw new SQLException("Không thể lấy kết nối cơ sở dữ liệu");
            }
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade(
                    rs.getInt("GradeID"),
                    rs.getInt("TutoringClassID"),
                    rs.getInt("UserID"),
                    rs.getFloat("Score"),
                    rs.getString("AssessmentDate"),
                    rs.getString("Note")
                );
                grades.add(grade);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy điểm số cho userID: " + userID, e);
            throw e;
        } finally {
            closeResources(rs, stmt, conn);
        }
        return grades;
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