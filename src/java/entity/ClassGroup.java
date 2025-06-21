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

    public ClassGroup() {
    }

    public ClassGroup(int classGroupId, int toturID, String name, int maxStudent, int teachId) {
        this.classGroupId = classGroupId;
        this.toturID = toturID;
        this.name = name;
        this.maxStudent = maxStudent;
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

    
    public int getTeachId() {
        return teachId;
    }

    public void setTeachId(int teachId) {
        this.teachId = teachId;
    }

    @Override
    public String toString() {
        return "ClassGroup{" + "classGroupId=" + classGroupId + ", toturID=" + toturID + ", name=" + name + ", maxStudent=" + maxStudent + ", teachId=" + teachId + '}';
    }

    
}
