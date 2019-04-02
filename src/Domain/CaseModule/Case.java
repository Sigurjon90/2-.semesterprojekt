package Domain.CaseModule;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Case {

    private static int numberOfCases = 0;
    private int caseID;
    private Date date;
    private String description;
    private String caseType;
    private List<File> attachedFiles;

    public Case(String description, String caseType) {
        this.date = new Date();
        this.description = description;
        this.caseType = caseType;
        attachedFiles = new ArrayList<>();
        numberOfCases++;
        this.caseID = numberOfCases;
    }

    public Case(String description, String caseType, List<File> attachedFiles) {
        this.date = new Date();
        this.description = description;
        this.caseType = caseType;
        this.attachedFiles = attachedFiles;
        numberOfCases++;
        this.caseID = numberOfCases;
    }

    public int getCaseID() {
        return this.caseID;
    }

    public String editCase() {
        return "The Case with ID: " + getCaseID() + " has been successfully edited \n";
    }

    public String attachFile(File file) {
        return "The File has been successfully attached to the case with the ID: " + getCaseID() + "\n";
    }

    public static void decrementCases() {
        numberOfCases--;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public List<File> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<File> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    @Override
    public String toString() {
        return "ID: " + this.caseID + " with type " + this.caseType + " created on: " + this.date + "\n";
    }

}
