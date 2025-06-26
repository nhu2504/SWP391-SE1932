package entity;

public class Document {
    private int documentID;
    private String title;
    private String description;
    private String filePath;
    private int uploadedBy;
    private int subjectID;

    public Document() {
    }

    public Document(int documentID, String title, String description, String filePath, int uploadedBy, int subjectID) {
        this.documentID = documentID;
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.uploadedBy = uploadedBy;
        this.subjectID = subjectID;
    }

    public int getDocumentID() {
        return documentID;
    }

    public void setDocumentID(int documentID) {
        this.documentID = documentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(int uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }
}