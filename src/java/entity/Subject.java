package entity;

public class Subject {
    private int subjectId;
    private String subjectName;
    private String imageSubject;
    private int classCount;

    public Subject() {
    }

    
    
    // Constructor cho danh sách môn học
    public Subject(int subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    // Constructor cho danh sách môn học với ảnh và số lớp
    public Subject(String imageSubject, int classCount) {
        this.imageSubject = imageSubject;
        this.classCount = classCount;
    }

    public Subject(int subjectId, String subjectName, String imageSubject) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.imageSubject = imageSubject;
    }
    

    // Getters
    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getImageSubject() {
        return imageSubject;
    }

    public int getClassCount() {
        return classCount;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setImageSubject(String imageSubject) {
        this.imageSubject = imageSubject;
    }

    public void setClassCount(int classCount) {
        this.classCount = classCount;
    }
    
    

    @Override
    public String toString() {
        return "Subject{" + "subjectId=" + subjectId + ", subjectName=" + subjectName + ", imageSubject=" + imageSubject + ", classCount=" + classCount + '}';
    }
    
}