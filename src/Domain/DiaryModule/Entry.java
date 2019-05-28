package Domain.DiaryModule;

import java.io.File;
import java.time.LocalDate;

public class Entry {

    private LocalDate date;
    private String description;
    private File file;
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

    public Entry(LocalDate date, String description, File file) {
        this.file = file;
        this.date = date;
        this.description = description;
    }

    public void editEntry(String description, LocalDate date, File file) {
        this.description = description;
        this.date = date;
        this.file = file;
    }

    public File getFile() {
        return file;
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

    public String fileNames() {
        String result = "";
        if (file != null) {
             result += file.getName();
        }
        return result;
    }

    @Override
    public String toString() {
        return date.toString();
    }
}
