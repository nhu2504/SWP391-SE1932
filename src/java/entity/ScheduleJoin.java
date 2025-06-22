/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import java.sql.Time;
import java.util.Date;
/**
 *
 * @author NGOC ANH
 */
public class ScheduleJoin {
    private int scheId;
    private String classGroupName;
    private Time start_time;
    private Time end_time;
    private String roomName;
    private Date dateLearn;
    private int userId;

    public ScheduleJoin() {
    }

    public ScheduleJoin(int scheId, String classGroupName, Time start_time, Time end_time, String roomName, Date dateLearn) {
        this.scheId = scheId;
        this.classGroupName = classGroupName;
        this.start_time = start_time;
        this.end_time = end_time;
        this.roomName = roomName;
        this.dateLearn = dateLearn;
    }

    public ScheduleJoin(int scheId, String classGroupName, Time start_time, Time end_time, String roomName, Date dateLearn, int userId) {
        this.scheId = scheId;
        this.classGroupName = classGroupName;
        this.start_time = start_time;
        this.end_time = end_time;
        this.roomName = roomName;
        this.dateLearn = dateLearn;
        this.userId = userId;
    }
    

    public int getScheId() {
        return scheId;
    }

    public void setScheId(int scheId) {
        this.scheId = scheId;
    }

    public String getClassGroupName() {
        return classGroupName;
    }

    public void setClassGroupName(String classGroupName) {
        this.classGroupName = classGroupName;
    }

    public Time getStart_time() {
        return start_time;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public Time getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Time end_time) {
        this.end_time = end_time;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Date getDateLearn() {
        return dateLearn;
    }

    public void setDateLearn(Date dateLearn) {
        this.dateLearn = dateLearn;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    

    @Override
    public String toString() {
        return "ScheduleJoin{" + "scheId=" + scheId + ", classGroupName=" + classGroupName + ", start_time=" + start_time + ", end_time=" + end_time + ", roomName=" + roomName + ", dateLearn=" + dateLearn + '}';
    }
    
    
}
