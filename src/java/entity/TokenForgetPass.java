/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDateTime;

/**
 *
 * @author NGOC ANH
 */
public class TokenForgetPass {

    private int tokenId;
    private String token;
    private LocalDateTime expTime;
    private boolean isUsed;
    private int userId;

    public TokenForgetPass() {
    }

    public TokenForgetPass(int tokenId, String token, LocalDateTime expTime, boolean isUsed, int userId) {
        this.tokenId = tokenId;
        this.token = token;
        this.expTime = expTime;
        this.isUsed = isUsed;
        this.userId = userId;
    }

    public TokenForgetPass(String token, LocalDateTime expTime, boolean isUsed, int userId) {
        this.token = token;
        this.expTime = expTime;
        this.isUsed = isUsed;
        this.userId = userId;
    }
    

    public int getTokenId() {
        return tokenId;
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpTime() {
        return expTime;
    }

    public void setExpTime(LocalDateTime expTime) {
        this.expTime = expTime;
    }

    public boolean isIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public boolean isExpired() {
        return expTime != null && LocalDateTime.now().isAfter(expTime);
    }

    @Override
    public String toString() {
        return "TokenForgetPass{" + "tokenId=" + tokenId + ", token=" + token + ", expTime=" + expTime + ", isUsed=" + isUsed + ", userId=" + userId + '}';
    }
    
    
}
