package Domain.CaseModule;

import Domain.User.Resident;
import Persistence.UserManager;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Case {

    //private static int numberOfCases = 0;
    private int caseID;
    private Date date;
    private String description;
    private String caseType;
    private ArrayList<String> attachedFiles;
    private boolean closed;
    private Resident caseResident;
    private String title;
    private int residentID;

//    public Case(String title, String description, String caseType) {
//        this.title = title;
//        this.date = new LocalDate();
//        this.description = description;
//        this.caseType = caseType;
//        attachedFiles = new ArrayList<>();
//        this.caseResident = new Resident();
//        numberOfCases++;
//        this.caseID = numberOfCases;
//    }
//
//    public Case(String title, String description, String caseType, Resident resident) {
//        this.title = title;
//        this.date = new Date();
//        this.description = description;
//        this.caseType = caseType;
//        attachedFiles = new ArrayList<>();
//        this.caseResident = resident;
//        numberOfCases++;
//        this.caseID = numberOfCases;
//    }
//    public Case(String title, String description, String caseType, List<File> attachedFiles) {
//        this.title = title;
//        this.date = new Date();
//        this.description = description;
//        this.caseType = caseType;
//        this.attachedFiles = attachedFiles;
//        this.caseResident = new Resident();
//        numberOfCases++;
//        this.caseID = numberOfCases;
//    }
//    public Case(String title, String description, String caseType, List<File> attachedFiles, Resident resident) {
//        this.title = title;
//        this.date = new Date();
//        this.description = description;
//        this.caseType = caseType;
//        this.attachedFiles = attachedFiles;
//        this.caseResident = resident;
//        numberOfCases++;
//        this.caseID = numberOfCases;
//    }
//////////////////////////////////////////////////////////////////////////////////////////////
//    public Case(String title, String description, String caseType, String date, Boolean isClosed, int residentID) {
//        this.title = title;
//
//        this.date = new LocalDate(date);
//        this.description = description;
//        this.caseType = caseType;
//        numberOfCases++;
//        this.caseID = numberOfCases;
//
//        this.closed = isClosed;
//
//    }
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

    public Resident getCaseResident() {
        return caseResident;
    }

    public void attachFile(String file) {
        if (!this.closed) {
            attachedFiles.add(file);
        }
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
                //   + "\n" + "Vedhæftet filer: " + fileNames()
                + "\n" + "Tilknyttet beboer: " + UserManager.getUser(this.residentID).getFirstName()
                + " " + UserManager.getUser(this.residentID).getLastName()
                + "\n" + "Sagen omhandler: "
                + "\n" + this.getDescription();
    }

//    private String fileNames() {
//        String result = "";
//        for (File f : this.attachedFiles) {
//            result += f.getName() + ", ";
//        }
//        return result;
//    }
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

    public ArrayList<String> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(ArrayList<String> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    @Override
    public String toString() {
        return "Sag ID: " + this.caseID + "\n";
    }
}
