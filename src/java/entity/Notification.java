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
public class Notification {
    private int notifiID;
    private String tittle;
    private String content;
    private Date createAt;
    private int targetRole;
    private int createBy;

    public Notification() {
    }

    public Notification(int notifiID, String tittle, String content, Date createAt, int targetRole, int createBy) {
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

    public int getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(int targetRole) {
        this.targetRole = targetRole;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    @Override
    public String toString() {
        return "Notification{" + "notifiID=" + notifiID + ", tittle=" + tittle + ", content=" + content + ", createAt=" + createAt + ", targetRole=" + targetRole + ", createBy=" + createBy + '}';
    }


}
