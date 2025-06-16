package entity;

public class Grade {
   private int gradeID;
   private String gradeName;

    public Grade() {
    }

    public Grade(int gradeID, String gradeName) {
        this.gradeID = gradeID;
        this.gradeName = gradeName;
    }

    public int getGradeID() {
        return gradeID;
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    @Override
    public String toString() {
        return "Grade{" + "gradeID=" + gradeID + ", gradeName=" + gradeName + '}';
    }
   
}