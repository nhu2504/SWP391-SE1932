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
public class Attendance {
    private int classGroup;
    private int userID;
    private Date attendanceDate;
    private boolean isPresent;

    public Attendance() {
    }

    public Attendance(int classGroup, int userID, Date attendanceDate, boolean isPresent) {
        this.classGroup = classGroup;
        this.userID = userID;
        this.attendanceDate = attendanceDate;
        this.isPresent = isPresent;
    }

    public int getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(int classGroup) {
        this.classGroup = classGroup;
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

    public boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }

    @Override
    public String toString() {
        return "Attendance{" + "classGroup=" + classGroup + ", userID=" + userID + ", attendanceDate=" + attendanceDate + ", isPresent=" + isPresent + '}';
    }

    

}