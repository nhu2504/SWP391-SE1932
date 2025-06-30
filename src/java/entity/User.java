/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;
import java.util.List;

/**
 *
 * @author DO NGOC ANH HE180661
 * 
 */
public class User {
    
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
    private int schoolID;
    private List<SchoolClass> schoolClasses;
    private List<Subject> subjects;
    private int roleID;
    private boolean isHot;
    private String parentEmail;
    private String parentPhone;
    private String schoolName;
private String schoolClassName;
private int schoolClassId;


    public User() {
    }

    public User(int id, String name, String gender, Date birth, String phone, String email, String password, String avatar, int status, Date createDate, String certi, String descrip, int schoolID, List<SchoolClass> schoolClasses, List<Subject> subjects, int roleID, boolean isHot, String parentEmail, String parentPhone) {
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
        this.schoolID = schoolID;
        this.schoolClasses = schoolClasses;
        this.subjects = subjects;
        this.roleID = roleID;
        this.isHot = isHot;
        this.parentEmail = parentEmail;
        this.parentPhone = parentPhone;
    }
    

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    

    public User(String email,String name,  String avatar) {
         this.email = email;
        this.name = name;
        this.avatar = avatar;
    }
    

    public User(int id, String email, String password, int roleID) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roleID = roleID;
    }

    public User(String email, String password, int roleID) {
        this.email = email;
        this.password = password;
        this.roleID = roleID;
    }

    

    public User(String email, String password) {
        this.email = email;
        this.password = password;
       
    }

    
    

    public User(int id, String name, String gender, String phone, String email, String avatar, int status, String certi, String descrip) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.status = status;
        this.certi = certi;
        this.descrip = descrip;
    }

    public int getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(int schoolClassId) {
        this.schoolClassId = schoolClassId;
    }
    

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolClassName() {
        return schoolClassName;
    }

    public void setSchoolClassName(String schoolClassName) {
        this.schoolClassName = schoolClassName;
    }
    
    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
    

    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public void setSchoolClasses(List<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
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

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    
    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
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

    public boolean isIsHot() {
        return isHot;
    }

    public void setIsHot(boolean isHot) {
        this.isHot = isHot;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", gender=" + gender + ", birth=" + birth + ", phone=" + phone + ", email=" + email + ", password=" + password + ", avatar=" + avatar + ", status=" + status + ", createDate=" + createDate + ", certi=" + certi + ", descrip=" + descrip + ", schoolID=" + schoolID + ", schoolClasses=" + schoolClasses + ", roleID=" + roleID + ", isHot=" + isHot + ", parentEmail=" + parentEmail + ", parentPhone=" + parentPhone + '}';
    }

   
    

}
