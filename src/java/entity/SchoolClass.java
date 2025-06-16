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
    private School school;
    private Grade grade;

    public SchoolClass() {
    }

    public SchoolClass(int schoolClassID, String className, School school) {
        this.schoolClassID = schoolClassID;
        this.className = className;
        this.school = school;
    }

    public SchoolClass(int schoolClassID, String className, School school, Grade grade) {
        this.schoolClassID = schoolClassID;
        this.className = className;
        this.school = school;
        this.grade = grade;
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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "SchoolClass{" + "schoolClassID=" + schoolClassID + ", className=" + className + ", school=" + school + ", grade=" + grade + '}';
    }
    
    
    
}
