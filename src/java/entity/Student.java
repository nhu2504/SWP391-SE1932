package entity;

import java.sql.Date;

public class Student {
    private int userId;
    private String fullName;
    private String avatar;
    private String certi;
    private String email;
    private String commentText;
    private Date commentDate;
    private int rating;
    private int achievementCount;

    public Student(int userId, String fullName, String avatar, String certi, String email,
                   String commentText, Date commentDate, int rating, int achievementCount) {
        this.userId = userId;
        this.fullName = fullName;
        this.avatar = avatar;
        this.certi = certi;
        this.email = email;
        this.commentText = commentText;
        this.commentDate = commentDate;
        this.rating = rating;
        this.achievementCount = achievementCount;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCerti() {
        return certi;
    }

    public String getEmail() {
        return email;
    }

    public String getCommentText() {
        return commentText;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public int getRating() {
        return rating;
    }

    public int getAchievementCount() {
        return achievementCount;
    }
}