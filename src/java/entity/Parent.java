package entity;

public class Parent {
    private String studentName;
    private String commentText;
    private int rating;

    public Parent(String studentName, String commentText, int rating) {
        this.studentName = studentName;
        this.commentText = commentText;
        this.rating = rating;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCommentText() {
        return commentText;
    }

    public int getRating() {
        return rating;
    }
}