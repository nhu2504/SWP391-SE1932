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
public class TutoringRegisPending {
    private int trpID;
    private String fullName;
    private String phone;
    private String email;
    private Date regisDate;
    private String status;
    private String gender;
    private Date birthDate;
    private String school;
    private String classAtSchool;
    private String parentPhone;
    private String parentEmail;
    private String avatar;
    private int confirm;

    public TutoringRegisPending() {
    }

    public TutoringRegisPending(int trpID, String fullName, String phone, String email, Date regisDate, String status, String gender, Date birthDate, String school, String classAtSchool, String parentPhone, String parentEmail, String avatar, int confirm) {
        this.trpID = trpID;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.regisDate = regisDate;
        this.status = status;
        this.gender = gender;
        this.birthDate = birthDate;
        this.school = school;
        this.classAtSchool = classAtSchool;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
        this.avatar = avatar;
        this.confirm = confirm;
    }

    public int getTrpID() {
        return trpID;
    }

    public void setTrpID(int trpID) {
        this.trpID = trpID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(Date regisDate) {
        this.regisDate = regisDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getClassAtSchool() {
        return classAtSchool;
    }

    public void setClassAtSchool(String classAtSchool) {
        this.classAtSchool = classAtSchool;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    @Override
    public String toString() {
        return "TutoringRegisPending{" + "trpID=" + trpID + ", fullName=" + fullName + ", phone=" + phone + ", email=" + email + ", regisDate=" + regisDate + ", status=" + status + ", gender=" + gender + ", birthDate=" + birthDate + ", school=" + school + ", classAtSchool=" + classAtSchool + ", parentPhone=" + parentPhone + ", parentEmail=" + parentEmail + ", avatar=" + avatar + ", confirm=" + confirm + '}';
    }
    
}
