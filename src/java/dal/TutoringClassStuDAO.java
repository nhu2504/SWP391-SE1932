package dal;

import entity.TutoringClassStu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 * DAO để lấy thông tin khóa học của học sinh, bao gồm trạng thái thanh toán.
 * @author DO NGOC ANH HE180661
 */
public class TutoringClassStuDAO {
    public ArrayList<TutoringClassStu> getClassesByUserID(int userID) {
        ArrayList<TutoringClassStu> classes = new ArrayList<>();
        String query = "SELECT DISTINCT tc.TutoringClassID, tc.ClassName, tc.StartDate, tc.Tuitionfee, " +
                      "CASE WHEN p.PaymentID IS NOT NULL THEN 1 ELSE 0 END AS isPaid " +
                      "FROM TutoringClass tc " +
                      "JOIN ClassGroup cg ON tc.TutoringClassID = cg.TutoringClassID " +
                      "JOIN ClassGroup_Student cgs ON cg.ClassGroupID = cgs.ClassGroupID " +
                      "LEFT JOIN Payment p ON tc.TutoringClassID = p.TutoringClassID AND p.UserID = ? " +
                      "WHERE cgs.StudentID = ? AND cgs.IsActive = 1";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, userID);
            ps.setInt(2, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                TutoringClassStu tutoringClass = new TutoringClassStu();
                tutoringClass.setTutoringClassID(rs.getInt("TutoringClassID"));
                tutoringClass.setClassName(rs.getString("ClassName"));
                tutoringClass.setStartDate(rs.getDate("StartDate"));
                tutoringClass.setFee(rs.getDouble("Tuitionfee"));
                tutoringClass.setPaid(rs.getBoolean("isPaid"));
                classes.add(tutoringClass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    public void updatePaymentStatus(int tutoringClassID, int userID) throws SQLException {
        String query = "IF EXISTS (SELECT 1 FROM Payment WHERE TutoringClassID = ? AND UserID = ?) " +
                      "UPDATE Payment SET PaymentDate = GETDATE() " +
                      "ELSE " +
                      "INSERT INTO Payment (UserID, TutoringClassID, Amount, PaymentDate) " +
                      "SELECT ?, ?, Tuitionfee, GETDATE() FROM TutoringClass WHERE TutoringClassID = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, tutoringClassID);
            ps.setInt(2, userID);
            ps.setInt(3, userID);
            ps.setInt(4, tutoringClassID);
            ps.setInt(5, tutoringClassID);
            ps.executeUpdate();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}