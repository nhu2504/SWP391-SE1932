package entity;

import java.sql.Date;

public class User {
    private int userID;
    private String fullName;
    private String email;
    private String roleName;
    private int roleID;
    private Date birthDate;
    private String phone;
    private String avatar;

    public User() {
    }

    public User(int userID, String fullName, String email, String roleName, int roleID, Date birthDate, String phone, String avatar) {
        this.userID = userID;
        this.fullName = fullName;
        this.email = email;
        this.roleName = roleName;
        this.roleID = roleID;
        this.birthDate = birthDate;
        this.phone = phone;
        this.avatar = avatar;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", fullName=" + fullName + ", email=" + email + ", roleName=" + roleName + ", roleID=" + roleID + ", birthDate=" + birthDate + ", phone=" + phone + ", avatar=" + avatar + '}';
    }

}