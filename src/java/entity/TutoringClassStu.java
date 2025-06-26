package entity;

import java.util.Date;

/**
 * Lớp TutoringClassStu đại diện cho khóa học của học sinh, bao gồm thông tin thanh toán.
 * @author DO NGOC ANH HE180661
 */
public class TutoringClassStu {
    private int tutoringClassID;
    private String className;
    private Date startDate;
    private boolean isPaid;
    private double fee;

    public int getTutoringClassID() {
        return tutoringClassID;
    }

    public void setTutoringClassID(int tutoringClassID) {
        this.tutoringClassID = tutoringClassID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}