package entity;

import java.math.BigDecimal;
import java.sql.Date;
// Văn Thị Như -HE181329
public class HomeTutoringClass {
    private int tutoringClassID;
    private String className;
    private String imageTutoring;
    private String descrip;
    private boolean isHot;
    private int subjectID;
    private Date startDate;
    private Date endDate;
    private BigDecimal tuitionFee;
    private Integer userID; // Nullable
    private int roomID;
    private int shiftID;
    private Integer thuID; // Nullable
    private int maxStudents; // Thêm MaxStudents

    public HomeTutoringClass(int tutoringClassID, String className, String imageTutoring, String descrip,
                             boolean isHot, int subjectID, Date startDate, Date endDate, BigDecimal tuitionFee,
                             Integer userID, int roomID, int shiftID, Integer thuID, int maxStudents) {
        this.tutoringClassID = tutoringClassID;
        this.className = className;
        this.imageTutoring = imageTutoring;
        this.descrip = descrip;
        this.isHot = isHot;
        this.subjectID = subjectID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tuitionFee = tuitionFee;
        this.userID = userID;
        this.roomID = roomID;
        this.shiftID = shiftID;
        this.thuID = thuID;
        this.maxStudents = maxStudents;
    }

    // Getters
    public int getTutoringClassID() { return tutoringClassID; }
    public String getClassName() { return className; }
    public String getImageTutoring() { return imageTutoring; }
    public String getDescrip() { return descrip; }
    public boolean isHot() { return isHot; }
    public int getSubjectID() { return subjectID; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public BigDecimal getTuitionFee() { return tuitionFee; }
    public Integer getUserID() { return userID; }
    public int getRoomID() { return roomID; }
    public int getShiftID() { return shiftID; }
    public Integer getThuID() { return thuID; }
    public int getMaxStudents() { return maxStudents; }
}