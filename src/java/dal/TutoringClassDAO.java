package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.TutoringClass;

public class TutoringClassDAO {
    private DBContext dbContext;

    public TutoringClassDAO() {
        dbContext = DBContext.getInstance();
    }

    public ArrayList<TutoringClass> getClassesByUserID(int userID) {
        ArrayList<TutoringClass> classes = new ArrayList<>();
        String sql = "SELECT tc.TutoringClassID, tc.ClassName, tc.SubjectID, tc.userID AS TeacherID, " +
                     "tc.StartDate, tc.EndDate, tc.roomID, tc.shiftID, tc.ThuID " +
                     "FROM TutoringClass tc " +
                     "JOIN TutoringRegistration tr ON tc.TutoringClassID = tr.TutoringClassID " +
                     "WHERE tr.UserID = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TutoringClass tc = new TutoringClass(
                    rs.getInt("TutoringClassID"),
                    rs.getString("ClassName"),
                    rs.getInt("SubjectID"),
                    rs.getInt("TeacherID"),
                    rs.getString("StartDate"),
                    rs.getString("EndDate"),
                    rs.getInt("roomID"),
                    rs.getInt("shiftID"),
                    rs.getInt("ThuID")
                );
                classes.add(tc);
            }
            System.out.println("TutoringClassDAO: classes size for userID " + userID + " = " + classes.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }
}