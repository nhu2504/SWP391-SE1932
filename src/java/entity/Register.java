/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author DO NGOC ANH HE180661
 */
public class Register {
    private int regisID;
    private String fullName;
    private String phone;
    private String email;
    private Date regisDate;
    private String approvalStatus;
    private String gender;
    private Date birth;
    private String school;
    private String address;
    private String classAtSchool;
    private String parentPhone;
    private String parentEmail;
    private int idUserIntro;
    private boolean confirm;

    public Register() {
    }

    public Register(String fullName, String phone, String email, String gender, Date birth, String school, String address, String classAtSchool, String parentPhone, String parentEmail, int idUserIntro, boolean confirm) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.school = school;
        this.address = address;
        this.classAtSchool = classAtSchool;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
        this.idUserIntro = idUserIntro;
        this.confirm = confirm;
    }

    public Register(int regisID, String fullName, String phone, String email, Date regisDate, String approvalStatus, String gender, Date birth, String school, String address, String classAtSchool, String parentPhone, String parentEmail, int idUserIntro, boolean confirm) {
        this.regisID = regisID;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.regisDate = regisDate;
        this.approvalStatus = approvalStatus;
        this.gender = gender;
        this.birth = birth;
        this.school = school;
        this.address = address;
        this.classAtSchool = classAtSchool;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
        this.idUserIntro = idUserIntro;
        this.confirm = confirm;
    }

    public int getRegisID() {
        return regisID;
    }

    public void setRegisID(int regisID) {
        this.regisID = regisID;
    }

    public Date getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(Date regisDate) {
        this.regisDate = regisDate;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
    

    public int getIdUserIntro() {
        return idUserIntro;
    }

    public void setIdUserIntro(int idUserIntro) {
        this.idUserIntro = idUserIntro;
    }

    

    

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
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

   

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    @Override
    public String toString() {
        return "Register{" + "regisID=" + regisID + ", fullName=" + fullName + ", phone=" + phone + ", email=" + email + ", regisDate=" + regisDate + ", approvalStatus=" + approvalStatus + ", gender=" + gender + ", birth=" + birth + ", school=" + school + ", address=" + address + ", classAtSchool=" + classAtSchool + ", parentPhone=" + parentPhone + ", parentEmail=" + parentEmail + ", idUserIntro=" + idUserIntro + ", confirm=" + confirm + '}';
    }

    
    
    
}

