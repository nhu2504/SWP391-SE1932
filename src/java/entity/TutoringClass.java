package entity;

import java.sql.Date;

public class TutoringClass {
    private int tutoringClassID;
    private String className;
    private String subjectName;
    private Date startDate;
    private Date endDate;
    private double tuitionFee;
    private String gradeName;

    public TutoringClass() {
    }

    public TutoringClass(int tutoringClassID, String className, String subjectName, Date startDate, Date endDate, double tuitionFee, String gradeName) {
        this.tutoringClassID = tutoringClassID;
        this.className = className;
        this.subjectName = subjectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tuitionFee = tuitionFee;
        this.gradeName = gradeName;
    }

    public int getTutoringClassID() {
        return tutoringClassID;
    }

    public void setTutoringClassID(int tutoringClassID) {
        this.tutoringClassID = tutoringClassID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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

    public double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    @Override
    public String toString() {
        return "TutoringClass{" + "tutoringClassID=" + tutoringClassID + ", className=" + className + ", subjectName=" + subjectName + ", startDate=" + startDate + ", endDate=" + endDate + ", tuitionFee=" + tuitionFee + ", gradeName=" + gradeName + '}';
    }

    
}