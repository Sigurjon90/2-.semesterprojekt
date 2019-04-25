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
    private String title;

    public Case(String title, String description, String caseType) {
        this.title = title;
        this.date = new Date();
        this.description = description;
        this.caseType = caseType;
        attachedFiles = new ArrayList<>();
        this.caseResident = new Resident();
        numberOfCases++;
        this.caseID = numberOfCases;
    }

    public Case(String title, String description, String caseType, Resident resident) {
        this.title = title;
        this.date = new Date();
        this.description = description;
        this.caseType = caseType;
        attachedFiles = new ArrayList<>();
        this.caseResident = resident;
        numberOfCases++;
        this.caseID = numberOfCases;
    }

    public Case(String title, String description, String caseType, List<File> attachedFiles) {
        this.title = title;
        this.date = new Date();
        this.description = description;
        this.caseType = caseType;
        this.attachedFiles = attachedFiles;
        this.caseResident = new Resident();
        numberOfCases++;
        this.caseID = numberOfCases;
    }

    public Case(String title, String description, String caseType, List<File> attachedFiles, Resident resident) {
        this.title = title;
        this.date = new Date();
        this.description = description;
        this.caseType = caseType;
        this.attachedFiles = attachedFiles;
        this.caseResident = resident;
        numberOfCases++;
        this.caseID = numberOfCases;
    }

    public void attachFile(File file) {
        if (!this.closed) {
            attachedFiles.add(file);
        }
    }

    public void closeCase(boolean closed, String reason) {
        this.description = description + "\nLukke grundlag: \n" + reason;
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
        }
    }

    public String getTitle() {
        return this.title;
    }

    public String getCaseType() {
        return caseType;
    }

    public List<File> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<File> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    @Override
    public String toString() {
        return "Sag ID: " + this.caseID + "\n";
    }

    public String showInformation() {
        return "Sagen er oprettet :" + this.getDate()
                + "\n" + "Titel: " + this.title
                + "\n" + "Sag ID: " + this.caseID
                + "\n" + "Vedhæftet filer: " + fileNames()
                + "\n" + "Tilknyttet beboer: " + this.caseResident.getFirstName()
                + " " + this.caseResident.getLastName()
                + "\n" + "Sagen omhandler: "
                + "\n" + this.getDescription();
    }

    private String fileNames() {
        String result = "";
        for (File f : this.attachedFiles) {
            result += f.getName() + ", ";
        }
        return result;
    }
}
