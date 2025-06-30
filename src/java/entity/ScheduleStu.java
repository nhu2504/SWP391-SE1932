package entity;

import java.util.Date;

/**
 * Entity representing a student's schedule
 * @author DO NGOC ANH HE180661
 */
public class ScheduleStu {
    private int scheduleID;
    private int classGroupID;
    private int shiftID;
    private int roomID;
    private Date dateLearn;
    private int teacherID;
    private String subjectName;
    private String roomName;

    public ScheduleStu() {
    }

    public ScheduleStu(int scheduleID, int classGroupID, int shiftID, int roomID, Date dateLearn, 
                      int teacherID, String subjectName, String roomName) {
        this.scheduleID = scheduleID;
        this.classGroupID = classGroupID;
        this.shiftID = shiftID;
        this.roomID = roomID;
        this.dateLearn = dateLearn;
        this.teacherID = teacherID;
        this.subjectName = subjectName;
        this.roomName = roomName;
    }

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

    @Override
    public String toString() {
        return "ScheduleStu{" +
               "scheduleID=" + scheduleID +
               ", classGroupID=" + classGroupID +
               ", shiftID=" + shiftID +
               ", roomID=" + roomID +
               ", dateLearn=" + dateLearn +
               ", teacherID=" + teacherID +
               ", subjectName='" + subjectName + '\'' +
               ", roomName='" + roomName + '\'' +
               '}';
    }
}