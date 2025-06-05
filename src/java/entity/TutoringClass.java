package entity;

public class TutoringClass {
    private int tutoringClassID;
    private String className;
    private Subject subjectID;
    private account teacherID;
    private String startDate;
    private String endDate;
    private Room roomID;
    private Shift shiftID;
    private Thu thuID;

    public TutoringClass() {
    }

    public TutoringClass(int tutoringClassID, String className, Subject subjectID, account teacherID, String startDate, String endDate, Room roomID, Shift shiftID, Thu thuID) {
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

    public Subject getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Subject subjectID) {
        this.subjectID = subjectID;
    }

    public account getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(account teacherID) {
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

    public Room getRoomID() {
        return roomID;
    }

    public void setRoomID(Room roomID) {
        this.roomID = roomID;
    }

    public Shift getShiftID() {
        return shiftID;
    }

    public void setShiftID(Shift shiftID) {
        this.shiftID = shiftID;
    }

    public Thu getThuID() {
        return thuID;
    }

    public void setThuID(Thu thuID) {
        this.thuID = thuID;
    }

    @Override
    public String toString() {
        return "TutoringClass{" + "tutoringClassID=" + tutoringClassID + ", className=" + className + ", subjectID=" + subjectID + ", teacherID=" + teacherID + ", startDate=" + startDate + ", endDate=" + endDate + ", roomID=" + roomID + ", shiftID=" + shiftID + ", thuID=" + thuID + '}';
    }

   

    
}