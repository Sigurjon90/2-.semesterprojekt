package Domain.DiaryModule;

import java.io.File;
import java.util.Date;
import java.util.List;

public class Entry {

    Date date;
    //todo Bruger creator;
    String description;
    List<Integer> accessType;
    List<File> files;
    int id;
    static int idCounter = 1; 

    

    public Entry(Date date, String description, List<Integer> accessType, List<File> files) {
        this.id = idCounter;
        this.date = date;
        this.description = description;
        this.accessType = accessType;
        this.files = files;
        idCounter++;
    }

    public Entry() {

    }

    public Entry(String description) {
        this.description = description;
    }

    public void editEntry(String description, List<Integer> accessType, List<File> files) {
        this.description = description;
        this.accessType = accessType;
        this.files = files;
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
    public String toString(){
        return description;
    }
     public int getId() {
        return id;
    }

}
