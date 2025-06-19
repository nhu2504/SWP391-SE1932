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
    private int tutorRegisId;
    private int userId;
    private int tutorId;
    private Date regisDate;

    public TutoringRegis() {
    }

    public TutoringRegis(int tutorRegisId, int userId, int tutorId, Date regisDate) {
        this.tutorRegisId = tutorRegisId;
        this.userId = userId;
        this.tutorId = tutorId;
        this.regisDate = regisDate;
    }

    public int getTutorRegisId() {
        return tutorRegisId;
    }

    public void setTutorRegisId(int tutorRegisId) {
        this.tutorRegisId = tutorRegisId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public Date getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(Date regisDate) {
        this.regisDate = regisDate;
    }

    @Override
    public String toString() {
        return "TutoringRegis{" + "tutorRegisId=" + tutorRegisId + ", userId=" + userId + ", tutorId=" + tutorId + ", regisDate=" + regisDate + '}';
    }
    
}
