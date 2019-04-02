package Domæne.KalenderModul;

import java.awt.Image;
import java.util.Date;

public class Aktivitet {

    private String place;
    private String description;
    private String type;
    private Image pictogram;
    private Date endDate;
    private Date startDate;
    private Boolean shared;
//waiting for Bruger implemtation
    private Bruger creator;

//Waiting for Bruger implemtation
    public Aktivitet (Bruger creator, String place, Date startDate,Date endDate, String description, String type, Image pictogram, Boolean shared){
        this.creator = creator;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.type = type;
        this.pictogram = pictogram;
        this.shared = shared;
    }
    public Aktivitet getActivity() {
        return null;
    }

    public void updateActivity(Bruger creator, String place, Date startDate,Date endDate, String description, String type, Image pictogram, Boolean shared) {
        this.creator = creator;
        this.place = place;
        this.startDate = startDate;
        this.description = description;
        this.type = type;
        this.pictogram = pictogram;
        this.endDate = endDate;
        this.shared = shared;
    }

}
