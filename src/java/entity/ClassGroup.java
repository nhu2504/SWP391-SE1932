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
    private int teachId;
    private int currentStudentCount;
    private String roomName;
    private int roomId;
    private int shiftId;
    
    
    

    public ClassGroup() {
    }

    public ClassGroup(int classGroupId, int toturID, String name, int maxStudent, int teachId, int roomId, int shiftId) {
        this.classGroupId = classGroupId;
        this.toturID = toturID;
        this.name = name;
        this.maxStudent = maxStudent;
        this.teachId = teachId;
        this.roomId = roomId;
        this.shiftId = shiftId;
    }

    
    public ClassGroup(int classGroupId, int toturID, String name, int maxStudent, int teachId) {
        this.classGroupId = classGroupId;
        this.toturID = toturID;
        this.name = name;
        this.maxStudent = maxStudent;
        this.teachId = teachId;
    }

    public ClassGroup(int classGroupId, int toturID, String name, int maxStudent, int teachId, int currentStudentCount) {
        this.classGroupId = classGroupId;
        this.toturID = toturID;
        this.name = name;
        this.maxStudent = maxStudent;
        this.teachId = teachId;
        this.currentStudentCount = currentStudentCount;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    

    public int getCurrentStudentCount() {
        return currentStudentCount;
    }

    public void setCurrentStudentCount(int currentStudentCount) {
        this.currentStudentCount = currentStudentCount;
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

    
    public int getTeachId() {
        return teachId;
    }

    public void setTeachId(int teachId) {
        this.teachId = teachId;
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

    @Override
    public String toString() {
        return "ClassGroup{" + "classGroupId=" + classGroupId + ", toturID=" + toturID + ", name=" + name + ", maxStudent=" + maxStudent + ", teachId=" + teachId + '}';
    }

    
}
