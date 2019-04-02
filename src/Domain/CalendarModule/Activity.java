package Domain.CalendarModule;

import Domain.User.User;
import java.awt.Image;
import java.util.Date;

public class Activity {

    private String place;
    private String description;
    private String type;
    private Image pictogram;
    private Date endDate;
    private Date startDate;
    private Boolean shared;
//waiting for Bruger implemtation
    private User creator;

//Waiting for Bruger implemtation
    public Activity(User creator, String place, Date startDate, Date endDate, String description, String type, Image pictogram, Boolean shared) {
        this.creator = creator;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.type = type;
        this.pictogram = pictogram;
        this.shared = shared;
    }

    public Activity getActivity() {
        return null;
    }

    public void updateActivity(User creator, String place, Date startDate, Date endDate, String description, String type, Image pictogram, Boolean shared) {
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
