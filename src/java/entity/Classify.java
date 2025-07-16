/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author NGOC ANH
 */
public class Classify {
    private int classifyId;
    private String typeDoc;

    public Classify(int classifyId, String typeDoc) {
        this.classifyId = classifyId;
        this.typeDoc = typeDoc;
    }

    public Classify() {
    }

    public int getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(int classifyId) {
        this.classifyId = classifyId;
    }

    public String getTypeDoc() {
        return typeDoc;
    }

    public void setTypeDoc(String typeDoc) {
        this.typeDoc = typeDoc;
    }

    @Override
    public String toString() {
        return "Classify{" + "classifyId=" + classifyId + ", typeDoc=" + typeDoc + '}';
    }
    
}
