/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NGOC ANH
 */
public class SchoolClass {
    private int schoolClassID;
    private String className;
    private int schoolID;
    private int gradeID;

    public SchoolClass() {
    }

    public SchoolClass(int schoolClassID, String className, int schoolID) {
        this.schoolClassID = schoolClassID;
        this.className = className;
        this.schoolID = schoolID;
    }

    public SchoolClass(int schoolClassID, String className, int schoolID, int gradeID) {
        this.schoolClassID = schoolClassID;
        this.className = className;
        this.schoolID = schoolID;
        this.gradeID = gradeID;
    }
    public int getSchoolClassID() {
        return schoolClassID;
    }

    public void setSchoolClassID(int schoolClassID) {
        this.schoolClassID = schoolClassID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public int getGradeID() {
        return gradeID;
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }
    @Override
    public String toString() {
        return "SchoolClass{" + "schoolClassID=" + schoolClassID + ", className=" + className + ", schoolID=" + schoolID + ", gradeID=" + gradeID + '}';
    }
}
