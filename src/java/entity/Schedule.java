package entity;

import java.sql.Date;

public class Schedule {
    private int scheduleID;
    private String classGroupName;
    private String shiftTime;
    private String roomName;
    private Date dateLearn;
    private String teacherName;

    public Schedule() {
    }

    public Schedule(int scheduleID, String classGroupName, String shiftTime, String roomName, Date dateLearn, String teacherName) {
        this.scheduleID = scheduleID;
        this.classGroupName = classGroupName;
        this.shiftTime = shiftTime;
        this.roomName = roomName;
        this.dateLearn = dateLearn;
        this.teacherName = teacherName;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getClassGroupName() {
        return classGroupName;
    }

    public void setClassGroupName(String classGroupName) {
        this.classGroupName = classGroupName;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Date getDateLearn() {
        return dateLearn;
    }

    public void setDateLearn(Date dateLearn) {
        this.dateLearn = dateLearn;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "Schedule{" + "scheduleID=" + scheduleID + ", classGroupName=" + classGroupName + ", shiftTime=" + shiftTime + ", roomName=" + roomName + ", dateLearn=" + dateLearn + ", teacherName=" + teacherName + '}';
    }

    
}