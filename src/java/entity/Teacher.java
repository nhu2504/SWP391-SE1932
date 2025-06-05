package entity;

public class Teacher {
    private int userId;
    private String fullName;
    private String gender;
    private String phone;
    private String email;
    private String avatar;
    private String certi;
    private String description;
    private boolean onlineStatus;

    public Teacher(int userId, String fullName, String gender, String phone, String email,
                   String avatar, String certi, String description, boolean onlineStatus) {
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.certi = certi;
        this.description = description;
        this.onlineStatus = onlineStatus;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCerti() {
        return certi;
    }

    public String getDescription() {
        return description;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }
}