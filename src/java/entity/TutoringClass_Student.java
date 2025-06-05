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
public class TutoringClass_Student {
    private int tsid;
    private TutoringClass tutorID;
    private account studentID;
    private Date enrollDate;

    public TutoringClass_Student() {
    }

    public TutoringClass_Student(int tsid, TutoringClass tutorID, account studentID, Date enrollDate) {
        this.tsid = tsid;
        this.tutorID = tutorID;
        this.studentID = studentID;
        this.enrollDate = enrollDate;
    }

    public int getTsid() {
        return tsid;
    }

    public void setTsid(int tsid) {
        this.tsid = tsid;
    }

    public TutoringClass getTutorID() {
        return tutorID;
    }

    public void setTutorID(TutoringClass tutorID) {
        this.tutorID = tutorID;
    }

    public account getStudentID() {
        return studentID;
    }

    public void setStudentID(account studentID) {
        this.studentID = studentID;
    }

    public Date getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }

    @Override
    public String toString() {
        return "TutoringClass_Student{" + "tsid=" + tsid + ", tutorID=" + tutorID + ", studentID=" + studentID + ", enrollDate=" + enrollDate + '}';
    }
    
    
}
