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
public class ClassGroup_Student {
    private int csId;
    private int classGroupId;
    private int studentId;
    private Date enrollDate;
    private boolean isActive;

    public ClassGroup_Student() {
    }

    public ClassGroup_Student(int csId, int classGroupId, int studentId, Date enrollDate, boolean isActive) {
        this.csId = csId;
        this.classGroupId = classGroupId;
        this.studentId = studentId;
        this.enrollDate = enrollDate;
        this.isActive = isActive;
    }

    public int getCsId() {
        return csId;
    }

    public void setCsId(int csId) {
        this.csId = csId;
    }

    public int getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(int classGroupId) {
        this.classGroupId = classGroupId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Date getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "ClassGroup_Student{" + "csId=" + csId + ", classGroupId=" + classGroupId + ", studentId=" + studentId + ", enrollDate=" + enrollDate + ", isActive=" + isActive + '}';
    }
    
    
}
