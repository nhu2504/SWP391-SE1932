/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

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

    @Override
    public String toString() {
        return "Schedule{" + "scheID=" + scheID + ", tutorId=" + classgroupID + ", shiftId=" + shiftId + ", roomId=" + roomId + ", dateLearn=" + dateLearn + ", userId=" + userId + '}';
    }

    
}
