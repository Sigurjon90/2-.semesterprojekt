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
    private boolean closed = false;

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

    public String editCase() {
        if (!this.closed) {
            
            return "The Case with ID: " + getCaseID() + " has been successfully edited \n";
        }
        return "The case is closed and cannot be edited";
    }

    public String attachFile(File file) {
        if (!this.closed) {
            attachedFiles.add(file);
            return "The File has been successfully attached to the case with the ID: " + getCaseID() + "\n";
        }
        return "The case is close and cannot attach new files";
    }

    public static void decrementCases() {
        numberOfCases--;
    }

    public void closeCase(boolean closed, String argument) {
        this.description = description + "\n Closing argument: \n" + argument;
        this.closed = closed;
    }

    public boolean isClosed(){
        return this.closed;
    }
    
    public int getCaseID() {
        return this.caseID;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        if (!this.closed) {
            this.description = description;
        }
    }

    public String getCaseType() {
        return caseType;
    }

    public List<File> getAttachedFiles() {
        return attachedFiles;
    }

    private void setAttachedFiles(List<File> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    @Override
    public String toString() {
        return "ID: " + this.caseID + " with type " + this.caseType + " created on: " + this.date + "\n";
    }

}
