package Domain.DiaryModule;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class Entry {

    private LocalDate date;
    private String description;
    private List<File> files;
    private int id;

    public Entry(LocalDate date, String description, int id) {
        this.date = date;
        this.description = description;
        this.id = id;
    }

    public Entry(LocalDate date, String description) {
        this.date = date;
        this.description = description;

    }

    public Entry(LocalDate date, String description, List<File> files) {
        this.files = files;
        this.date = date;
        this.description = description;
    }

    public void editEntry(String description, LocalDate date, List<File> files) {
        this.description = description;
        this.date = date;
        this.files = files;
    }

    public String getEntryDescription() {
        return description;
    }

    public String getEntryDescriptionAndFiles() {
        return description + "\n" + fileNames();
    }

    public int getid() {
        return id;
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
