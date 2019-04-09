package Domain.CalendarModule;

import Domain.User.User;
import java.awt.Image;
import java.util.Date;

public class Activity {

    private String place;
    private String description;
    private String type;
    private Image pictogram = null;
    private Date endDate;
    private Date startDate;
    private Boolean shared;
    private User creator;
    private String title;
    private static int idCounter = 1;
    private int id;
    private String endDate2;
    private String startDate2;

    public Activity(String title, User creator, String place, Date startDate, Date endDate, String description, String type, Boolean shared) {
        this.title = title;
        this.creator = creator;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.type = type;
        this.shared = shared;
        this.id = idCounter;
        idCounter++;
    }

    // Testmetode
    public Activity(String title, User creator, String place, String startDate, String endDate, String description, String type, Boolean shared) {
        this.title = title;
        this.creator = creator;
        this.place = place;
        this.startDate2 = startDate;
        this.endDate2 = endDate;
        this.description = description;
        this.type = type;
        this.shared = shared;
        this.id = idCounter;
        idCounter++;
    }

    public int getId() {
        return id;
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
