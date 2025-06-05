/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NGOC ANH
 */
public class Parent_Student {
    private account parent;
    private account student;

    public Parent_Student() {
    }

    public Parent_Student(account parent, account student) {
        this.parent = parent;
        this.student = student;
    }

    public account getParent() {
        return parent;
    }

    public void setParent(account parent) {
        this.parent = parent;
    }

    public account getStudent() {
        return student;
    }

    public void setStudent(account student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Parent_Student{" + "parent=" + parent + ", student=" + student + '}';
    }
    
}
