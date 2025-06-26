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
    private Time startTime;
    private Time endTime;
    private String roomName;
    private Date dateLearn;
    private int userId;
    private int classGroupId;
    private Date startDate;
    private Date endDate;
    private String teacherName;
    private int roomId;
    private int tutorId;
    public ScheduleJoin() {
    }

    public ScheduleJoin(int scheId, String classGroupName, Time startTime, Time endTime, String roomName, Date dateLearn) {
        this.scheId = scheId;
        this.classGroupName = classGroupName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomName = roomName;
        this.dateLearn = dateLearn;
    }

    public ScheduleJoin(int scheId, String classGroupName, Time startTime, Time endTime, String roomName, Date dateLearn, int userId) {
        this.scheId = scheId;
        this.classGroupName = classGroupName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomName = roomName;
        this.dateLearn = dateLearn;
        this.userId = userId;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }
    
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public int getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(int classGroupId) {
        this.classGroupId = classGroupId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time end_time) {
        this.endTime = endTime;
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
        return "ScheduleJoin{" + "scheId=" + scheId + ", classGroupName=" + classGroupName + ", start_time=" + startTime + ", end_time=" + endTime + ", roomName=" + roomName + ", dateLearn=" + dateLearn + '}';
    }
    
    
}
