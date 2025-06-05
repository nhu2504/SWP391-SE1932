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
    private String fullName;
    private String phone;
    private String email;
    private String gender;
    private Date birth;
    
    private String school;
    private String classAtSchool;
    private String parentPhone;
    private String parentEmail;
    private String avatar;
    private boolean confirm;

    public Register() {
    }

    public Register(String fullName, String phone, String email, String gender, Date birth, String school, String classAtSchool, String parentPhone, String parentEmail, String avatar, boolean confirm) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.school = school;
        this.classAtSchool = classAtSchool;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
        this.avatar = avatar;
        this.confirm = confirm;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    @Override
    public String toString() {
        return "Register{" + "fullName=" + fullName + ", phone=" + phone + ", email=" + email + ", gender=" + gender + ", birth=" + birth + ", school=" + school + ", classAtSchool=" + classAtSchool + ", parentPhone=" + parentPhone + ", parentEmail=" + parentEmail + ", avatar=" + avatar + ", confirm=" + confirm + '}';
    }

   

   
    
    
}

