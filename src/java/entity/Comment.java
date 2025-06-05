/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author NGOC ANH
 */
public class Comment {
    private int commentID;
    private TutoringClass tutoringClass;
    private account userID;
    private String commentText;
    private Date commentDate;
    private int rating;

    public Comment() {
    }

    public Comment(int commentID, TutoringClass tutoringClass, account userID, String commentText, Date commentDate, int rating) {
        this.commentID = commentID;
        this.tutoringClass = tutoringClass;
        this.userID = userID;
        this.commentText = commentText;
        this.commentDate = commentDate;
        this.rating = rating;
    }

    

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public TutoringClass getTutoringClass() {
        return tutoringClass;
    }

    public void setTutoringClass(TutoringClass tutoringClass) {
        this.tutoringClass = tutoringClass;
    }

    public account getUserID() {
        return userID;
    }

    public void setUserID(account userID) {
        this.userID = userID;
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
        return "Comment{" + "commentID=" + commentID + ", tutoringClass=" + tutoringClass + ", userID=" + userID + ", commentText=" + commentText + ", commentDate=" + commentDate + ", rating=" + rating + '}';
    }

    
    
}
