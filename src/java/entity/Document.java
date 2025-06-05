package entity;

import java.util.Date;

public class Document {
    private int documentID;
    private String title;
    private String description;
    
    private account uploadedBy;
    private Date uploadDate;
    private Subject subjectID;

    public Document() {
    }

    public Document(int documentID, String title, String description, account uploadedBy, Date uploadDate, Subject subjectID) {
        this.documentID = documentID;
        this.title = title;
        this.description = description;
        this.uploadedBy = uploadedBy;
        this.uploadDate = uploadDate;
        this.subjectID = subjectID;
    }

    public Document(int documentID, String title, String description, Subject subjectID) {
        this.documentID = documentID;
        this.title = title;
        this.description = description;
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

    

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public account getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(account uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Subject getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Subject subjectID) {
        this.subjectID = subjectID;
    }

    @Override
    public String toString() {
        return "Document{" + "documentID=" + documentID + ", title=" + title + ", description=" + description + ", uploadedBy=" + uploadedBy + ", uploadDate=" + uploadDate + ", subjectID=" + subjectID + '}';
    }

    

    
    
    
    
}