package entity;

import java.util.Date;

public class TutoringClass {
    private int tutoringClassID;
    private String className;
    private String image;
    private String descrip;
    private boolean isHot;
    private int subjectID;
    private Date startDate;
    private Date endDate;
    private double price;
    private int teacherID;
    private int roomID;
    private int shiftID;
    private int maxStudent;
    private int gradeID;

    public TutoringClass() {
    }

    public TutoringClass(int tutoringClassID, String className, String image, String descrip, boolean isHot, int subjectID, Date startDate, Date endDate, double price, int teacherID, int roomID, int shiftID, int maxStudent, int gradeID) {
        this.tutoringClassID = tutoringClassID;
        this.className = className;
        this.image = image;
        this.descrip = descrip;
        this.isHot = isHot;
        this.subjectID = subjectID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.teacherID = teacherID;
        this.roomID = roomID;
        this.shiftID = shiftID;
        this.maxStudent = maxStudent;
        this.gradeID = gradeID;
    }

    
    

    public TutoringClass(int tutoringClassID, String className, int subjectID, Date startDate, Date endDate, int teacherID, int roomID, int shiftID) {
        this.tutoringClassID = tutoringClassID;
        this.className = className;
        this.subjectID = subjectID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacherID = teacherID;
        this.roomID = roomID;
        this.shiftID = shiftID;
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

    public int getGradeID() {
        return gradeID;
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
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

    

    

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public boolean isIsHot() {
        return isHot;
    }

    public void setIsHot(boolean isHot) {
        this.isHot = isHot;
    }

    

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    

    public int getMaxStudent() {
        return maxStudent;
    }

    public void setMaxStudent(int maxStudent) {
        this.maxStudent = maxStudent;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    @Override
    public String toString() {
        return "TutoringClass{" + "tutoringClassID=" + tutoringClassID + ", className=" + className + ", image=" + image + ", descrip=" + descrip + ", isHot=" + isHot + ", subjectID=" + subjectID + ", startDate=" + startDate + ", endDate=" + endDate + ", price=" + price + ", teacherID=" + teacherID + ", roomID=" + roomID + ", shiftID=" + shiftID + ", maxStudent=" + maxStudent + ", gradeID=" + gradeID + '}';
    }        
}