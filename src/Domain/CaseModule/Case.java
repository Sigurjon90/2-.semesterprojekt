package Domain.CaseModule;

import Domain.User.Resident;
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
    private Resident caseResident;

    public Case(String description, String caseType) {
        this.date = new Date();
        this.description = description;
        this.caseType = caseType;
        attachedFiles = new ArrayList<>();
        this.caseResident = new Resident();
        numberOfCases++;
        this.caseID = numberOfCases;
    }

    public Case(String description, String caseType, Resident resident) {
        this.date = new Date();
        this.description = description;
        this.caseType = caseType;
        attachedFiles = new ArrayList<>();
        this.caseResident = resident;
        numberOfCases++;
        this.caseID = numberOfCases;
    }

    public Case(String description, String caseType, List<File> attachedFiles) {
        this.date = new Date();
        this.description = description;
        this.caseType = caseType;
        this.attachedFiles = attachedFiles;
        this.caseResident = new Resident();
        numberOfCases++;
        this.caseID = numberOfCases;
    }

    public Case(String description, String caseType, List<File> attachedFiles, Resident resident) {
        this.date = new Date();
        this.description = description;
        this.caseType = caseType;
        this.attachedFiles = attachedFiles;
        this.caseResident = resident;
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

    public void closeCase(boolean closed, String reason) {
        this.description = description + "\nReason for closing: \n" + reason;
        this.closed = closed;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public int getCaseID() {
        return this.caseID;
    }

    public Date getDate() {
        return this.date;
    }
    
    public Resident getResident() {
        return this.caseResident;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        if (!this.closed) {
            this.description = description;
        } else {
            System.out.println("Case is close. \"" + description + "\" cannot be set as description");
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
        return "Sag ID: " + this.caseID + "\n";
    }

    public String showInformation() {
        return "Sagen er oprettet :" + this.getDate() + 
                "\n" + "Sag ID: " + this.caseID + 
                "\n" + "Tilknyttet beboer: " + this.caseResident.getFirstName() 
                + " " + this.caseResident.getLastName() + 
                "\n" + "Sagen omhandler: " + 
                "\n" + this.getDescription();
    }

}
