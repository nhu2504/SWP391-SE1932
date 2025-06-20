/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author DO NGOC ANH HE180661
 * 
 */
public class ClassGroup {
    
    private int classGroupId;
    private int toturID;
    private String name;
    private int maxStudent;
    private int roomId;
    private int shiftId;
    private int teachId;

    public ClassGroup() {
    }

    public ClassGroup(int classGroupId, int toturID, String name, int maxStudent, int roomId, int shiftId, int teachId) {
        this.classGroupId = classGroupId;
        this.toturID = toturID;
        this.name = name;
        this.maxStudent = maxStudent;
        this.roomId = roomId;
        this.shiftId = shiftId;
        this.teachId = teachId;
    }

    public int getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(int classGroupId) {
        this.classGroupId = classGroupId;
    }

    public int getToturID() {
        return toturID;
    }

    public void setToturID(int toturID) {
        this.toturID = toturID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxStudent() {
        return maxStudent;
    }

    public void setMaxStudent(int maxStudent) {
        this.maxStudent = maxStudent;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public int getTeachId() {
        return teachId;
    }

    public void setTeachId(int teachId) {
        this.teachId = teachId;
    }

    @Override
    public String toString() {
        return "ClassGroup{" + "classGroupId=" + classGroupId + ", toturID=" + toturID + ", name=" + name + ", maxStudent=" + maxStudent + ", roomId=" + roomId + ", shiftId=" + shiftId + ", teachId=" + teachId + '}';
    }
    

}
