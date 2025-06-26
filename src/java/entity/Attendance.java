package entity;

import java.util.Date;

public class Attendance {
    private int classGroupID;
    private int userID;
    private Date attendanceDate;
    private boolean isPresent;

    // Getters and Setters
    public int getClassGroupID() {
        return classGroupID;
    }

    public void setClassGroupID(int classGroupID) {
        this.classGroupID = classGroupID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}