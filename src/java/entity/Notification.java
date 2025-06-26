package entity;

import java.util.Date;

public class Notification {
    private int notificationID;
    private String title;
    private String content;
    private Date createdAt;
    private int targetRole;
    private int createdBy;
    private boolean read;
    private boolean important;

    public Notification() {}

    public Notification(int notificationID, String title, String content, Date createdAt, int targetRole, int createdBy, boolean read, boolean important) {
        this.notificationID = notificationID;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.targetRole = targetRole;
        this.createdBy = createdBy;
        this.read = read;
        this.important = important;
    }

    public int getNotificationID() { return notificationID; }
    public void setNotificationID(int notificationID) { this.notificationID = notificationID; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public int getTargetRole() { return targetRole; }
    public void setTargetRole(int targetRole) { this.targetRole = targetRole; }
    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
    public boolean isImportant() { return important; }
    public void setImportant(boolean important) { this.important = important; }
}