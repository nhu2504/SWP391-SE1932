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
    private String address;

    public School() {
    }
    
    
    public School(int schoolID, String name) {
        this.schoolID = schoolID;
        this.name = name;
    }

    public School(int schoolID, String name, String address) {
        this.schoolID = schoolID;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "School{" + "schoolID=" + schoolID + ", name=" + name + ", address=" + address + '}';
    }
    
    
    
    
    
}
