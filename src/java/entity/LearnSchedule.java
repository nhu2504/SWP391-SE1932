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
public class LearnSchedule {
    private Room roomID;
    private Date daylearn;
    private Shift shiftID;
    private TutoringClass tutoringClassID;
    private Thu thuID;
    private account userID;

    public LearnSchedule() {
    }

    public LearnSchedule(Room roomID, Date daylearn, Shift shiftID, TutoringClass tutoringClassID, Thu thuID, account userID) {
        this.roomID = roomID;
        this.daylearn = daylearn;
        this.shiftID = shiftID;
        this.tutoringClassID = tutoringClassID;
        this.thuID = thuID;
        this.userID = userID;
    }

    public Room getRoomID() {
        return roomID;
    }

    public void setRoomID(Room roomID) {
        this.roomID = roomID;
    }

    public Date getDaylearn() {
        return daylearn;
    }

    public void setDaylearn(Date daylearn) {
        this.daylearn = daylearn;
    }

    public Shift getShiftID() {
        return shiftID;
    }

    public void setShiftID(Shift shiftID) {
        this.shiftID = shiftID;
    }

    public TutoringClass getTutoringClassID() {
        return tutoringClassID;
    }

    public void setTutoringClassID(TutoringClass tutoringClassID) {
        this.tutoringClassID = tutoringClassID;
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

    @Override
    public String toString() {
        return "LearnSchedule{" + "roomID=" + roomID + ", daylearn=" + daylearn + ", shiftID=" + shiftID + ", tutoringClassID=" + tutoringClassID + ", thuID=" + thuID + ", userID=" + userID + '}';
    }
    
    
}
