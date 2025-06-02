/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NGOC ANH
 */
public class School {
    private int schoolID;
    private String name;

    public School() {
    }
    
    
    public School(int schoolID, String name) {
        this.schoolID = schoolID;
        this.name = name;
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "School{" + "schoolID=" + schoolID + ", name=" + name + '}';
    }
    
    
    
}
