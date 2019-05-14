package Domain.DiaryModule;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Entry {

    private LocalDate date;
    //todo User creator;
    private String description;
    private List<Integer> accessType;
    private List<File> files;
    private String entryID;
    public static int idCounter = 1;
    private boolean visible = true;

    public Entry(LocalDate date, String description, String UUID) {
        //this.entryID = UUID.randomUUID().toString();
        this.date = date;
        this.description = description;
        this.entryID = UUID;
        //System.out.println("udprint af entryid:" + entryID);
    }

    public Entry(LocalDate date, String description) {
        //this.entryID = UUID.randomUUID().toString();
        this.date = date;
        this.description = description;
        //System.out.println("udprint af entryid:" + entryID);
    }

//    public Entry(LocalDate date, String description, List<File> files) {
//        this.files = files;
//        this.entryID = idCounter;
//        this.date = date;
//        this.description = description;
//        idCounter++;
//    }
    public void editEntry(String description, LocalDate date, List<File> files) {
        this.description = description;
        this.date = date;
        this.files = files;
    }

    public void deleteEntry() {
        this.description = description;
        this.date = date;
        this.files = files;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getEntryDescription() {
        return description;
    }

    public String getEntryDescriptionAndFiles() {
        return description + "\n" + fileNames();
    }

    public String getEntryID() {
        return entryID;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<File> getFiles() {
        return files;
    }

    public String fileNames() {
        String result = "";

        for (File file : files) {
            if (files.size() == 1) {
                result += file.getName();
            } else {
                result += file.getName() + ", ";
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return date.toString();
    }
}
