/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NGOC ANH
 */
public class Banner {
    private int bannerID;
    private String bannerImg;
    private int centerId;

    public Banner() {
    }

    public Banner(int bannerID, String bannerImg, int centerId) {
        this.bannerID = bannerID;
        this.bannerImg = bannerImg;
        this.centerId = centerId;
    }

   

    public int getBannerID() {
        return bannerID;
    }

    public void setBannerID(int bannerID) {
        this.bannerID = bannerID;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    @Override
    public String toString() {
        return "Banner{" + "bannerID=" + bannerID + ", bannerImg=" + bannerImg + ", centerId=" + centerId + '}';
    }

    
    
}
