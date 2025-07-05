/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author DO NGOC ANH HE180661
 * 
 */
public class ScheduleTemplate {
    private int tempID;
    private int classgroupID;
    private int dayOfWeek;
    private int shiftId;
    private int roomId;
    private int userId;

    public ScheduleTemplate() {
    }

    public ScheduleTemplate(int tempID, int classgroupID, int dayOfWeek, int shiftId, int roomId, int userId) {
        this.tempID = tempID;
        this.classgroupID = classgroupID;
        this.dayOfWeek = dayOfWeek;
        this.shiftId = shiftId;
        this.roomId = roomId;
        this.userId = userId;
    }

    

    public int getTempID() {
        return tempID;
    }

    public void setTempID(int tempID) {
        this.tempID = tempID;
    }

   

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
        return "ScheduleTemplate{" + "tempID=" + tempID + ", classgroupID=" + classgroupID + ", dayOfWeek=" + dayOfWeek + ", shiftId=" + shiftId + ", roomId=" + roomId + ", userId=" + userId + '}';
    }

    
    
    
}
