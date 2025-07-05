/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;
import java.sql.Time;
/**
 *
 * @author DO NGOC ANH HE180661
 *
 */
public class Schedule {

    private int scheID;
    private int classgroupID;
    private int shiftId;
    private int roomId;
    private Date dateLearn;
    private int userId;
    private String classgroupName;
    private String shiftName;
    private String roomName;
    private Time startTime;
    private Time endTime;
    private String teacherName;

    public Schedule() {
    }

    public Schedule(int scheID, int classgroupID, int shiftId, int roomId, Date dateLearn, int userId) {
        this.scheID = scheID;
        this.classgroupID = classgroupID;
        this.shiftId = shiftId;
        this.roomId = roomId;
        this.dateLearn = dateLearn;
        this.userId = userId;
    }
    

    public Schedule(int scheID, int classgroupID, int shiftId, int roomId, Date dateLearn, int userId, String classgroupName, String shiftName, String roomName) {
        this.scheID = scheID;
        this.classgroupID = classgroupID;
        this.shiftId = shiftId;
        this.roomId = roomId;
        this.dateLearn = dateLearn;
        this.userId = userId;
        this.classgroupName = classgroupName;
        this.shiftName = shiftName;
        this.roomName = roomName;
    }

    public Schedule(int scheID, int classgroupID, int shiftId, int roomId, Date dateLearn, int userId, String classgroupName, String shiftName, String roomName, Time startTime, Time endTime) {
        this.scheID = scheID;
        this.classgroupID = classgroupID;
        this.shiftId = shiftId;
        this.roomId = roomId;
        this.dateLearn = dateLearn;
        this.userId = userId;
        this.classgroupName = classgroupName;
        this.shiftName = shiftName;
        this.roomName = roomName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    
    

    public int getScheID() {
        return scheID;
    }

    public void setScheID(int scheID) {
        this.scheID = scheID;
    }

    public Date getDateLearn() {
        return dateLearn;
    }

    public void setDateLearn(Date dateLearn) {
        this.dateLearn = dateLearn;
    }

    public int getClassgroupID() {
        return classgroupID;
    }

    public void setClassgroupID(int classgroupID) {
        this.classgroupID = classgroupID;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getClassgroupName() {
        return classgroupName;
    }

    public void setClassgroupName(String classgroupName) {
        this.classgroupName = classgroupName;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    
    

    @Override
    public String toString() {
        return "Schedule{" + "scheID=" + scheID + ", classgroupID=" + classgroupID + ", shiftId=" + shiftId + ", roomId=" + roomId + ", dateLearn=" + dateLearn + ", userId=" + userId + '}';
    }

}
