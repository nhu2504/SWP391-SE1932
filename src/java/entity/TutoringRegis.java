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
public class TutoringRegis {
    private int tutorRegisID;
    private account userID;
    private TutoringClass tutorID;
    private Date regisDate;

    public TutoringRegis() {
    }

    public TutoringRegis(int tutorRegisID, account userID, TutoringClass tutorID, Date regisDate) {
        this.tutorRegisID = tutorRegisID;
        this.userID = userID;
        this.tutorID = tutorID;
        this.regisDate = regisDate;
    }

    public int getTutorRegisID() {
        return tutorRegisID;
    }

    public void setTutorRegisID(int tutorRegisID) {
        this.tutorRegisID = tutorRegisID;
    }

    public account getUserID() {
        return userID;
    }

    public void setUserID(account userID) {
        this.userID = userID;
    }

    public TutoringClass getTutorID() {
        return tutorID;
    }

    public void setTutorID(TutoringClass tutorID) {
        this.tutorID = tutorID;
    }

    public Date getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(Date regisDate) {
        this.regisDate = regisDate;
    }

    @Override
    public String toString() {
        return "TutoringRegis{" + "tutorRegisID=" + tutorRegisID + ", userID=" + userID + ", tutorID=" + tutorID + ", regisDate=" + regisDate + '}';
    }
    
    
}
