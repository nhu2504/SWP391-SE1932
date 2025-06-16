/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NGOC ANH
 */
public class ScheduleTemplate {
    private int templateID;
    private int classGroupID;
    private int dayOfWeek;
    private int shiftID;
    private int roomID;
    private int teacherID;

    public ScheduleTemplate() {
    }

    public ScheduleTemplate(int templateID, int classGroupID, int dayOfWeek, int shiftID, int roomID, int teacherID) {
        this.templateID = templateID;
        this.classGroupID = classGroupID;
        this.dayOfWeek = dayOfWeek;
        this.shiftID = shiftID;
        this.roomID = roomID;
        this.teacherID = teacherID;
    }

    public int getTemplateID() {
        return templateID;
    }

    public void setTemplateID(int templateID) {
        this.templateID = templateID;
    }

    public int getClassGroupID() {
        return classGroupID;
    }

    public void setClassGroupID(int classGroupID) {
        this.classGroupID = classGroupID;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    @Override
    public String toString() {
        return "ScheduleTemplate{" +
                "templateID=" + templateID +
                ", classGroupID=" + classGroupID +
                ", dayOfWeek=" + dayOfWeek +
                ", shiftID=" + shiftID +
                ", roomID=" + roomID +
                ", teacherID=" + teacherID +
                '}';
    }
}