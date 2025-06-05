package entity;

import java.sql.Date;
import java.sql.Time;
import java.math.BigDecimal;

public class TutoringClassDetails {
    private HomeTutoringClass tutoringClass;
    private String subjectName;
    private String teacherName;
    private Time startTime;
    private Time endTime;
    private String duration;
    private String thuName;

    public TutoringClassDetails(HomeTutoringClass tutoringClass, String subjectName, String teacherName,
                               Time startTime, Time endTime, String duration, String thuName) {
        this.tutoringClass = tutoringClass;
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.thuName = thuName;
    }

    // Getters
    public HomeTutoringClass getTutoringClass() {
        return tutoringClass;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getDuration() {
        return duration;
    }

    public String getThuName() {
        return thuName;
    }
}