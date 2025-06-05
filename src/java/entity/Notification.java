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
public class Notification {
    private int notifiID;
    private String tittle;
    private String content;
    private Date createAt;
    private roles targetRole;
    private account createBy;

    public Notification() {
    }

    public Notification(int notifiID, String tittle, String content, Date createAt, roles targetRole, account createBy) {
        this.notifiID = notifiID;
        this.tittle = tittle;
        this.content = content;
        this.createAt = createAt;
        this.targetRole = targetRole;
        this.createBy = createBy;
    }

    public int getNotifiID() {
        return notifiID;
    }

    public void setNotifiID(int notifiID) {
        this.notifiID = notifiID;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public roles getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(roles targetRole) {
        this.targetRole = targetRole;
    }

    public account getCreateBy() {
        return createBy;
    }

    public void setCreateBy(account createBy) {
        this.createBy = createBy;
    }

    @Override
    public String toString() {
        return "Notification{" + "notifiID=" + notifiID + ", tittle=" + tittle + ", content=" + content + ", createAt=" + createAt + ", targetRole=" + targetRole + ", createBy=" + createBy + '}';
    }
    
}
