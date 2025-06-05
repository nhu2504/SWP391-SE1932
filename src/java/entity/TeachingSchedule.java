/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author NGOC ANH
 */
public class TeachingSchedule {
    private TutoringClass tutorID;
    private Shift shiftID;
    private Room roomID;
    private Date date;
    private Thu thuID;
    private account userID;
    private int scheduleID;

    public TeachingSchedule() {
    }

    public TeachingSchedule(TutoringClass tutorID, Shift shiftID, Room roomID, Date date, Thu thuID, account userID, int scheduleID) {
        this.tutorID = tutorID;
        this.shiftID = shiftID;
        this.roomID = roomID;
        this.date = date;
        this.thuID = thuID;
        this.userID = userID;
        this.scheduleID = scheduleID;
    }

    public TutoringClass getTutorID() {
        return tutorID;
    }

    public void setTutorID(TutoringClass tutorID) {
        this.tutorID = tutorID;
    }

    public Shift getShiftID() {
        return shiftID;
    }

    public void setShiftID(Shift shiftID) {
        this.shiftID = shiftID;
    }

    public Room getRoomID() {
        return roomID;
    }

    public void setRoomID(Room roomID) {
        this.roomID = roomID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Thu getThuID() {
        return thuID;
    }

    public void setThuID(Thu thuID) {
        this.thuID = thuID;
    }

    public account getUserID() {
        return userID;
    }

    public void setUserID(account userID) {
        this.userID = userID;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    @Override
    public String toString() {
        return "TeachingSchedule{" + "tutorID=" + tutorID + ", shiftID=" + shiftID + ", roomID=" + roomID + ", date=" + date + ", thuID=" + thuID + ", userID=" + userID + ", scheduleID=" + scheduleID + '}';
    }
    
    
}
