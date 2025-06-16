package entity;

public class CenterInfo {
    private String nameCenter;
    private String addressCenter;
    private String email;
    private String phone;
    private String descripCenter;

    public CenterInfo(String nameCenter, String addressCenter, String email, String phone, String descripCenter) {
        this.nameCenter = nameCenter;
        this.addressCenter = addressCenter;
        this.email = email;
        this.phone = phone;
        this.descripCenter = descripCenter;
    }

    // Getters
    public String getNameCenter() {
        return nameCenter;
    }

    public String getAddressCenter() {
        return addressCenter;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescripCenter() {
        return descripCenter;
    }
}