package entity;

public class Thu {
    private int thuID;
    private String nameThu;

    public Thu() {
    }

    public Thu(int thuID, String nameThu) {
        this.thuID = thuID;
        this.nameThu = nameThu;
    }

    public int getThuID() {
        return thuID;
    }

    public void setThuID(int thuID) {
        this.thuID = thuID;
    }

    public String getNameThu() {
        return nameThu;
    }

    public void setNameThu(String nameThu) {
        this.nameThu = nameThu;
    }
}