package Domæne.SagModul;

import java.io.File;
import java.util.Date;
import java.util.List;

public class Sag {

    private Date date;
    private String description;
    private String caseType;
    private List<File> attachedFiles;

    public Sag() {
        this.date = new Date();
    }

    public String deleteCase() {
        return null;
    }

    public String editCase() {
        return null;
    }

    public String attachFile(File file) {
        return null;
    }
}
