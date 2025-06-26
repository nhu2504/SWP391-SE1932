package entity;

import java.util.Date;

public class Schedule {
    private int scheduleID;
    private int classGroupID;
    private int shiftID;
    private int roomID;
    private Date dateLearn;
    private int teacherID;
    private String subjectName;
    private String roomName;

    // Getters and Setters
    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public int getClassGroupID() {
        return classGroupID;
    }

    public void setClassGroupID(int classGroupID) {
        this.classGroupID = classGroupID;
    }

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public Date getDateLearn() {
        return dateLearn;
    }

    public void setDateLearn(Date dateLearn) {
        this.dateLearn = dateLearn;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}