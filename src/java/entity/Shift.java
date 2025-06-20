/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import java.sql.Time;
/**
 *
 * @author DO NGOC ANH HE180661
 * 
 */
public class Shift {
    private int id;
    private Time startTime;
    private Time endTime;

    public Shift() {
    }

    public Shift(int id, Time startTime, Time endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Shift(Time startTime, Time endTime) {
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

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

   

    @Override
    public String toString() {
        return "Shift{" + "id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }

    
    
    
}
