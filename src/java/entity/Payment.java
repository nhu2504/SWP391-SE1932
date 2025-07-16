/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author DO NGOC ANH HE180661
 * 
 */
public class Payment {
    private int paymentId;
    private int userId;
    private int tutorId;
    private double amount;
    private Date payDate;
    private boolean status;

    public Payment(int paymentId, int userId, int tutorId, double amount, Date payDate) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.tutorId = tutorId;
        this.amount = amount;
        this.payDate = payDate;
    }

    public Payment(int paymentId, int userId, int tutorId, double amount, Date payDate, boolean status) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.tutorId = tutorId;
        this.amount = amount;
        this.payDate = payDate;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return "Payment{" + "paymentId=" + paymentId + ", userId=" + userId + ", tutorId=" + tutorId + ", amount=" + amount + ", payDate=" + payDate + '}';
    }
    
    
}
