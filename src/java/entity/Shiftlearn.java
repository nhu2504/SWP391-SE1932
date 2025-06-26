package entity;

public class Shiftlearn {
    private int shiftID;
    private String startTime;
    private String endTime;

    public Shiftlearn() {
    }

    public Shiftlearn(int shiftID, String startTime, String endTime) {
        this.shiftID = shiftID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
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
}