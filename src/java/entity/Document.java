package entity;

public class Document {
    private int documentID;
    private String title;
    private String description;
    private String filePath;
    private String externalLink;
    private int uploadedBy;
    private String uploadDate;
    private Integer classID;

    public Document() {
    }

    public Document(int documentID, String title, String description, String filePath, String externalLink, int uploadedBy, String uploadDate, Integer classID) {
        this.documentID = documentID;
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.externalLink = externalLink;
        this.uploadedBy = uploadedBy;
        this.uploadDate = uploadDate;
        this.classID = classID;
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

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public int getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(int uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getClassID() {
        return classID;
    }

    public void setClassID(Integer classID) {
        this.classID = classID;
    }

    
}