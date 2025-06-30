package entity;

import java.util.Date;
/**
 *
 * @author DO NGOC ANH HE180661
 * 
 */
public class Document {
   private int documentId;
    private String title;
    private String description;
    private String pdfPath;
    private int uploadedBy;
    private Date uploadDate;
    private int subjectId;
    private int gradeId;

    public Document() {
    }

    public Document(int documentId, String title, String description, String pdfPath, int uploadedBy, Date uploadDate) {
        this.documentId = documentId;
        this.title = title;
        this.description = description;
        this.pdfPath = pdfPath;
        this.uploadedBy = uploadedBy;
        this.uploadDate = uploadDate;
    }

    public Document(String title, String description, String pdfPath) {
        this.title = title;
        this.description = description;
        this.pdfPath = pdfPath;
    }

    public Document(int documentId, String title, String description, String pdfPath, int uploadedBy, Date uploadDate, int subjectId, int gradeId) {
        this.documentId = documentId;
        this.title = title;
        this.description = description;
        this.pdfPath = pdfPath;
        this.uploadedBy = uploadedBy;
        this.uploadDate = uploadDate;
        this.subjectId = subjectId;
        this.gradeId = gradeId;
    }
    

    public Document(int documentId, String title, String description, int uploadedBy, Date uploadDate, int subjectId) {
        this.documentId = documentId;
        this.title = title;
        this.description = description;
        this.uploadedBy = uploadedBy;
        this.uploadDate = uploadDate;
        this.subjectId = subjectId;
    }

    public Document(int documentId, String title, String description, int uploadedBy, Date uploadDate, int subjectId, int gradeId) {
        this.documentId = documentId;
        this.title = title;
        this.description = description;
        this.uploadedBy = uploadedBy;
        this.uploadDate = uploadDate;
        this.subjectId = subjectId;
        this.gradeId = gradeId;
    }

    public Document(int documentId, String title, String description, int subjectId) {
        this.documentId = documentId;
        this.title = title;
        this.description = description;
        this.subjectId = subjectId;
    }

    public Document(int documentId, String title, String description, int subjectId, int gradeId) {
        this.documentId = documentId;
        this.title = title;
        this.description = description;
        this.subjectId = subjectId;
        this.gradeId = gradeId;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
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

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(int uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    @Override
    public String toString() {
        return "Document{" + "documentId=" + documentId + ", title=" + title + ", description=" + description + ", pdfPath=" + pdfPath + ", uploadedBy=" + uploadedBy + ", uploadDate=" + uploadDate + ", subjectId=" + subjectId + ", gradeId=" + gradeId + '}';
    }

    

    
    
}