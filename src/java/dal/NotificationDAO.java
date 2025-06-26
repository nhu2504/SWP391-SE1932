package dal;

import entity.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    private DBContext dbContext;

    public NotificationDAO() {
        dbContext = DBContext.getInstance();
    }

    public List<Notification> getPublicNotifications() throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT NotificationID, Title, Content, CreatedAt, TargetRole, CreatedBy " +
                     "FROM Notifications WHERE TargetRole = 3 OR TargetRole IS NULL " +
                     "ORDER BY CreatedAt DESC";

        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

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
        }
        return notifications;
    }

    public Notification getNotificationById(int notificationID) throws SQLException {
        String sql = "SELECT NotificationID, Title, Content, CreatedAt, TargetRole, CreatedBy " +
                     "FROM Notifications WHERE NotificationID = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notificationID);
            ResultSet rs = stmt.executeQuery();

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
        }
        return null;
    }
}