package Domain.CaseModule;

import Persistence.UserManager;
import java.util.ArrayList;
import java.sql.Date;

public class Case {

    private int caseID;
    private Date date;
    private String description;
    private String caseType;
    private ArrayList<String> attachedFiles;
    private boolean closed;
    private String title;
    private int residentID;

    public Case(String title, String description, String caseType, Date date, Boolean isClosed, int residentID, int id, ArrayList<String> fileList) {
        this.title = title;
        this.attachedFiles = fileList;
        this.date = date;
        this.description = description;
        this.caseType = caseType;

        this.caseID = id;
        this.residentID = residentID;

        this.closed = isClosed;
    }

    public Case(String title, String description, String caseType, Date date, Boolean isClosed, int residentID, ArrayList<String> fileList) {
        this.title = title;
        this.attachedFiles = fileList;
        this.date = date;
        this.description = description;
        this.caseType = caseType;

        this.caseID = 0;
        this.residentID = residentID;

        this.closed = isClosed;

    }

    public int getResidentID() {
        return this.residentID;
    }

    public void closeCase(boolean closed, String reason) {
        this.description = description + "\nLukke grundlag: \n" + reason;
        this.closed = closed;
    }

    public String showInformation() {
        return "Sagen er oprettet :" + this.getDate()
                + "\n" + "Titel: " + this.title
                + "\n" + "Sag ID: " + this.caseID
                + "\n" + "Sagstype: " + this.caseType
                + "\n" + "Filer tilknyttet " + this.getAttachedFiles()
                + "\n" + "Tilknyttet beboer: " + UserManager.getUser(this.residentID).getFirstName()
                + " " + UserManager.getUser(this.residentID).getLastName()
                + "\n" + "Sagen omhandler: "
                + "\n" + this.getDescription();
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

    public ArrayList<String> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(ArrayList<String> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    @Override
    public String toString() {
        if (!this.isClosed()) {
            return "Sag ID: " + this.caseID + "\n";
        } else {
            return "Sag ID: " + this.caseID + " " + "SAGEN ER LUKKET" + "\n";
        }
    }
}
