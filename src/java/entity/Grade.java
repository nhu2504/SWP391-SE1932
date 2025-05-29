package entity;

public class Grade {
    private int GradeID;
    private int TutoringClassID;
    private int UserID;
    private float Score;
    private String AssessmentDate;
    private String Note;

    public Grade() {
    }

    public Grade(int GradeID, int TutoringClassID, int UserID, float Score, String AssessmentDate, String Note) {
        this.GradeID = GradeID;
        this.TutoringClassID = TutoringClassID;
        this.UserID = UserID;
        this.Score = Score;
        this.AssessmentDate = AssessmentDate;
        this.Note = Note;
    }

    public int getGradeID() {
        return GradeID;
    }

    public void setGradeID(int GradeID) {
        this.GradeID = GradeID;
    }

    public int getTutoringClassID() {
        return TutoringClassID;
    }

    public void setTutoringClassID(int TutoringClassID) {
        this.TutoringClassID = TutoringClassID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public float getScore() {
        return Score;
    }

    public void setScore(float Score) {
        this.Score = Score;
    }

    public String getAssessmentDate() {
        return AssessmentDate;
    }

    public void setAssessmentDate(String AssessmentDate) {
        this.AssessmentDate = AssessmentDate;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

   
   
}