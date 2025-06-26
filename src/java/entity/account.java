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
public class account {
    
    private int id;
    private String name;
    private String gender;
    private Date birth;
    private String phone;
    private String email;
    private String password;
    private String avatar;
    private int status;
    private Date createDate;
    private String certi;
    private String descrip;
    private School school;
    private SchoolClass sclass;
    private roles role;
    private String parentEmail;
    private String parentPhone;
    
    
    
    
    

    public account() {
    }

    public account(int id, String email, String password, roles role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public account(String email, String password, roles role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public account(int id, String name, String gender, Date birth, String phone, String email, String password, String avatar, int status, Date createDate, String certi, String descrip, School school, SchoolClass sclass, roles role, String parentEmail, String parentPhone) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.status = status;
        this.createDate = createDate;
        this.certi = certi;
        this.descrip = descrip;
        this.school = school;
        this.sclass = sclass;
        this.role = role;
        this.parentEmail = parentEmail;
        this.parentPhone = parentPhone;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public roles getRole() {
        return role;
    }

    public void setRole(roles role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCerti() {
        return certi;
    }

    public void setCerti(String certi) {
        this.certi = certi;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public SchoolClass getSclass() {
        return sclass;
    }

    public void setSclass(SchoolClass sclass) {
        this.sclass = sclass;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    

    @Override
    public String toString() {
        return "account{" + "id=" + id + ", email=" + email + ", password=" + password + ", roleid=" + role + '}';
    }
    
    
}
