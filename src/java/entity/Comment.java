/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author DO NGOC ANH HE180661
 * 
 */
public class Comment {
    private int commentID;
    private int tutorID;
    private int userID;
    private String commentText;
    private Date commentDate;
    private int rating;
    private String fullName;
    private String avatar;
    private String certi;
    private String email;
    private int achievementCount;
    public Comment() {
    }

    public Comment(int commentID, int tutorID, int userID, String commentText, Date commentDate, int rating) {
        this.commentID = commentID;
        this.tutorID = tutorID;
        this.userID = userID;
        this.commentText = commentText;
        this.commentDate = commentDate;
        this.rating = rating;
    }

    public Comment(int commentID, int tutorID, int userID, String commentText, Date commentDate, int rating, String fullName, String avatar, String certi, String email, int achievementCount) {
        this.commentID = commentID;
        this.tutorID = tutorID;
        this.userID = userID;
        this.commentText = commentText;
        this.commentDate = commentDate;
        this.rating = rating;
        this.fullName = fullName;
        this.avatar = avatar;
        this.certi = certi;
        this.email = email;
        this.achievementCount = achievementCount;
    }

    public Comment(int userID, String commentText, Date commentDate, int rating, String fullName, String avatar, String certi, String email, int achievementCount) {
        this.userID = userID;
        this.commentText = commentText;
        this.commentDate = commentDate;
        this.rating = rating;
        this.fullName = fullName;
        this.avatar = avatar;
        this.certi = certi;
        this.email = email;
        this.achievementCount = achievementCount;
    }

    

    

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getTutorID() {
        return tutorID;
    }

    public void setTutorID(int tutorID) {
        this.tutorID = tutorID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCerti() {
        return certi;
    }

    public void setCerti(String certi) {
        this.certi = certi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAchievementCount() {
        return achievementCount;
    }

    public void setAchievementCount(int achievementCount) {
        this.achievementCount = achievementCount;
    }

    

    

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Comment{" + "commentID=" + commentID + ", tutorID=" + tutorID + ", userID=" + userID + ", commentText=" + commentText + ", commentDate=" + commentDate + ", rating=" + rating + ", fullName=" + fullName + ", avatar=" + avatar + ", certi=" + certi + ", email=" + email + ", achievementCount=" + achievementCount + '}';
    }

    

}
