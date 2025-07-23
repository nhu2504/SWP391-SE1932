/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NGOC ANH
 */
public class TeacherClass {

    private int teacherId, schoolClassId;
    private String scName;

    public TeacherClass() {
    }

    public TeacherClass(int teacherId, int schoolClassId) {
        this.teacherId = teacherId;
        this.schoolClassId = schoolClassId;
    }

    public String getScName() {
        return scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }
    
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(int schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    @Override
    public String toString() {
        return "TeacherClass{" + "teacherId=" + teacherId + ", schoolClassId=" + schoolClassId + '}';
    }
}
