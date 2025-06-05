package entity;

public class Grade {
    private TutoringClass tutoringClass;
    private account userID;
    private String nameOfTest;
    private float score;

    public Grade() {
    }

    public Grade(TutoringClass tutoringClass, account userID, String nameOfTest, float score) {
        this.tutoringClass = tutoringClass;
        this.userID = userID;
        this.nameOfTest = nameOfTest;
        this.score = score;
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

    

    

    public String getNameOfTest() {
        return nameOfTest;
    }

    public void setNameOfTest(String nameOfTest) {
        this.nameOfTest = nameOfTest;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Grade{" + "tutoringClass=" + tutoringClass + ", userID=" + userID + ", nameOfTest=" + nameOfTest + ", score=" + score + '}';
    }
    
}