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
public class CenterInfo {
    private int centerID;
    private String nameCenter;
    private String descrip;
    private String address;
    private String logo;
    private String banner;
    private String bannerTeacher;
    private String imageCenter;
    private String phone;
    private String email;
    private String website;
    private String face;
    private String zalo;
    private String youtube;
    private String tiktok;
    private Date lastUpdate;
    private account updateBy;

    public CenterInfo() {
    }

    public CenterInfo(String nameCenter, String address, String email, String phone, String descrip) {
        this.nameCenter = nameCenter;
        
        this.address = address;
       
        this.email = email;
         this.phone = phone;
        this.descrip = descrip;
    }

    public CenterInfo(int centerID, String nameCenter, String descrip, String address, String logo, String banner, 
            String bannerTeacher, String imageCenter, String phone, String email, String website, String face, String zalo, 
            String youtube, String tiktok, Date lastUpdate, account updateBy) {
        this.centerID = centerID;
        this.nameCenter = nameCenter;
        this.descrip = descrip;
        this.address = address;
        this.logo = logo;
        this.banner = banner;
        this.bannerTeacher = bannerTeacher;
        this.imageCenter = imageCenter;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.face = face;
        this.zalo = zalo;
        this.youtube = youtube;
        this.tiktok = tiktok;
        this.lastUpdate = lastUpdate;
        this.updateBy = updateBy;
    }
    
    

    public CenterInfo(int centerID, String nameCenter, String descrip, String address, String logo, String banner, 
            String bannerTeacher, String imageCenter, String phone, String email, Date lastUpdate, account updateBy) {
        this.centerID = centerID;
        this.nameCenter = nameCenter;
        this.descrip = descrip;
        this.address = address;
        this.logo = logo;
        this.banner = banner;
        this.bannerTeacher = bannerTeacher;
        this.imageCenter = imageCenter;
        this.phone = phone;
        this.email = email;
        this.lastUpdate = lastUpdate;
        this.updateBy = updateBy;
    }

    public CenterInfo(int centerID, String nameCenter, String descrip, String address, String logo, String banner, 
            String bannerTeacher, String imageCenter, String phone, String email, Date lastUpdate) {
        this.centerID = centerID;
        this.nameCenter = nameCenter;
        this.descrip = descrip;
        this.address = address;
        this.logo = logo;
        this.banner = banner;
        this.bannerTeacher = bannerTeacher;
        this.imageCenter = imageCenter;
        this.phone = phone;
        this.email = email;
        this.lastUpdate = lastUpdate;
    }

    public int getCenterID() {
        return centerID;
    }

    public void setCenterID(int centerID) {
        this.centerID = centerID;
    }

    public String getNameCenter() {
        return nameCenter;
    }

    public void setNameCenter(String nameCenter) {
        this.nameCenter = nameCenter;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBannerTeacher() {
        return bannerTeacher;
    }

    public void setBannerTeacher(String bannerTeacher) {
        this.bannerTeacher = bannerTeacher;
    }

    public String getImageCenter() {
        return imageCenter;
    }

    public void setImageCenter(String imageCenter) {
        this.imageCenter = imageCenter;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getZalo() {
        return zalo;
    }

    public void setZalo(String zalo) {
        this.zalo = zalo;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getTiktok() {
        return tiktok;
    }

    public void setTiktok(String tiktok) {
        this.tiktok = tiktok;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public account getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(account updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return "CenterInfo{" + "centerID=" + centerID + ", nameCenter=" + nameCenter + ", descrip=" + descrip + ", address=" + address + ", logo=" + logo + ", banner=" + banner + ", bannerTeacher=" + bannerTeacher + ", imageCenter=" + imageCenter + ", phone=" + phone + ", email=" + email + ", website=" + website + ", face=" + face + ", zalo=" + zalo + ", youtube=" + youtube + ", tiktok=" + tiktok + ", lastUpdate=" + lastUpdate + ", updateBy=" + updateBy + '}';
    }

    
   
    
    
}
