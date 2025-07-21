/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author NGOC ANH
 */
public class RequestActive {
    private int raId;
    private String name;
    private String email;
    private Date birth;
    private String school;
    private String classAtSchool;

    public RequestActive() {
    }

    public RequestActive(int raId, String name, String email, Date birth, String school, String classAtSchool) {
        this.raId = raId;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.school = school;
        this.classAtSchool = classAtSchool;
    }

    public RequestActive(String name, String email, Date birth, String school, String classAtSchool) {
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.school = school;
        this.classAtSchool = classAtSchool;
    }
    

    public int getRaId() {
        return raId;
    }

    public void setRaId(int raId) {
        this.raId = raId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getClassAtSchool() {
        return classAtSchool;
    }

    public void setClassAtSchool(String classAtSchool) {
        this.classAtSchool = classAtSchool;
    }

    @Override
    public String toString() {
        return "RequestAction{" + "raId=" + raId + ", name=" + name + ", email=" + email + ", birth=" + birth + ", school=" + school + ", classAtSchool=" + classAtSchool + '}';
    }
    
}
