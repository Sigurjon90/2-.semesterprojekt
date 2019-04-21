package Domain.DiaryModule;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Entry {

    LocalDate date;
    //todo Bruger creator;
    String description;
    List<Integer> accessType;
    List<File> files;
    int id;
    public static int idCounter = 1;

    public Entry(LocalDate date, String description, List<Integer> accessType, List<File> files) {
        this.id = idCounter;
        this.date = date;
        this.description = description;
        this.accessType = accessType;
        this.files = files;
        idCounter++;
    }

    public Entry() {
        this.id = idCounter;
        idCounter++;
    }

    public Entry(String description) {
        this.id = idCounter;
        this.description = description;
        idCounter++;
    }

    public Entry(LocalDate date, String description) {
        this.id = idCounter;
        this.date = date;
        this.description = description;
        idCounter++;

    }

    public Entry(LocalDate date, String description, List<File> files) {
        this.files = files;
        this.id = idCounter;
        this.date = date;
        this.description = description;
        idCounter++;
    }

    public void editEntry(String description, List<Integer> accessType, List<File> files) {
        this.description = description;
        this.accessType = accessType;
        this.files = files;
    }

    public void editDateAndDescription(String description, LocalDate date) {
        this.description = description;
        this.date = date;
    }

    public void editDescription(String description) {
        this.description = description;
    }

    public void editAccessType(List<Integer> accessType) {
        this.accessType = accessType;
    }

    public void editFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return date.toString();
    }

    public void deleteEntry() {

    }

    public String getEntryDescription() {
        return description;
    }

    public String getEntryDescriptionAndFile() {
        return description + "\n" + fileNames();
    }

    public String fileNames() {
        String result = "";

        for (File file : files) {
            result += file.getName() + ", ";
        }
        return result;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<File> getFiles() {
        return files;
    }
}
