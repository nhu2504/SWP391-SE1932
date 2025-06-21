/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
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
    public String getDurationText() {
        try {
            if (startTime == null || endTime == null) {
                return "Chưa xác định";
            }

            // Chuyển Time (java.sql.Time) sang LocalTime để tính toán
            LocalTime start = startTime.toLocalTime();
            LocalTime end = endTime.toLocalTime();

            // Tính khoảng cách giữa start và end
            Duration duration = Duration.between(start, end);

            // Nếu end < start (ca học qua đêm), cộng thêm 24 giờ
            if (duration.isNegative()) {
                duration = duration.plusHours(24);
            }

            // Chuyển sang giờ dưới dạng thập phân
            double hours = duration.toMinutes() / 60.0;

            // Trả kết quả theo định dạng: "2 giờ" hoặc "1.5 giờ"
            return hours % 1 == 0
                ? ((int) hours + " giờ")
                : String.format("%.1f giờ", hours);

        } catch (Exception e) {
            return "Chưa xác định";
        }
    }
   

    @Override
    public String toString() {
        return "Shift{" + "id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }

    
    
    
}
