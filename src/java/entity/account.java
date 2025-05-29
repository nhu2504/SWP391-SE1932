/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author DO NGOC ANH HE180661
 */
public class account {
    
    private int id;
    private String email;
    private String password;
    private roles role;

    public account() {
    }

    public account(int id, String email, String password, roles role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public account(String email, String password, roles role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public roles getRole() {
        return role;
    }

    public void setRole(roles role) {
        this.role = role;
    }

    

    @Override
    public String toString() {
        return "account{" + "id=" + id + ", email=" + email + ", password=" + password + ", roleid=" + role + '}';
    }
    
    
}
