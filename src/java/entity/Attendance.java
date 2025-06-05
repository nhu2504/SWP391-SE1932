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
public class Attendance {
    private TutoringClass tutoringClass;
    private int userID;
    private Date attendanceDate;
    private int isPresent;

    public Attendance() {
    }

    public Attendance(TutoringClass tutoringClass, int userID, Date attendanceDate, int isPresent) {
        this.tutoringClass = tutoringClass;
        this.userID = userID;
        this.attendanceDate = attendanceDate;
        this.isPresent = isPresent;
    }

    public TutoringClass getTutoringClass() {
        return tutoringClass;
    }

    public void setTutoringClass(TutoringClass tutoringClass) {
        this.tutoringClass = tutoringClass;
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

    public int getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }

    @Override
    public String toString() {
        return "Attendance{" + "tutoringClass=" + tutoringClass + ", userID=" + userID + ", attendanceDate=" + attendanceDate + ", isPresent=" + isPresent + '}';
    }

    
    
}
