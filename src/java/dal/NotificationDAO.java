package dal;

import entity.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    public List<Notification> getPublicNotifications() throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT NotificationID, Title, Content, CreatedAt, TargetRole, CreatedBy " +
                     "FROM Notifications WHERE TargetRole = 3 OR TargetRole IS NULL " +
                     "ORDER BY CreatedAt DESC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = new DBContext().connection;
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationID(rs.getInt("NotificationID"));
                notification.setTitle(rs.getString("Title"));
                notification.setContent(rs.getString("Content"));
                notification.setCreatedAt(rs.getTimestamp("CreatedAt"));
                notification.setTargetRole(rs.getInt("TargetRole"));
                notification.setCreatedBy(rs.getInt("CreatedBy"));
                notification.setRead(false);
                notification.setImportant(rs.getInt("NotificationID") % 2 == 0);
                notifications.add(notification);
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return notifications;
    }

    public Notification getNotificationById(int notificationID) throws SQLException {
        String sql = "SELECT NotificationID, Title, Content, CreatedAt, TargetRole, CreatedBy " +
                     "FROM Notifications WHERE NotificationID = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = new DBContext().connection;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, notificationID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationID(rs.getInt("NotificationID"));
                notification.setTitle(rs.getString("Title"));
                notification.setContent(rs.getString("Content"));
                notification.setCreatedAt(rs.getTimestamp("CreatedAt"));
                notification.setTargetRole(rs.getInt("TargetRole"));
                notification.setCreatedBy(rs.getInt("CreatedBy"));
                notification.setRead(false);
                notification.setImportant(rs.getInt("NotificationID") % 2 == 0);
                return notification;
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}