package entity;

public class TutoringClass {
    private int tutoringClassID;
    private String className;
    private int subjectID;
    private int teacherID;
    private String startDate;
    private String endDate;
    private int roomID;
    private int shiftID;
    private int thuID;

    public TutoringClass() {
    }

    public TutoringClass(int tutoringClassID, String className, int subjectID, int teacherID, String startDate, String endDate, int roomID, int shiftID, int thuID) {
        this.tutoringClassID = tutoringClassID;
        this.className = className;
        this.subjectID = subjectID;
        this.teacherID = teacherID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomID = roomID;
        this.shiftID = shiftID;
        this.thuID = thuID;
    }

    public int getTutoringClassID() {
        return tutoringClassID;
    }

    public void setTutoringClassID(int tutoringClassID) {
        this.tutoringClassID = tutoringClassID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public int getThuID() {
        return thuID;
    }

    public void setThuID(int thuID) {
        this.thuID = thuID;
    }
}