/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import java.sql.Time;
/**
 *
 * @author DO NGOC ANH HE180661
 */
public class Shift {
    private int id;
    private String startTime;
    private String endTime;

    public Shift() {
    }

    public Shift(int id, String startTime, String endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Shift(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Shift(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Shift{" + "id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }

    
    
    
}
