package Domain.CalendarModule;

import Domain.User.User;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calendar {

    private List<Activity> calender = new ArrayList<>();

    public void deleteActivity() {
    }

    public void editActivity() {
    }

    public void createActivity(User creator, String place, Date startDate, Date endDate, String description, String type, Image pictogram, Boolean shared) {
        calender.add(new Activity(creator, place, startDate, endDate, description, type, pictogram, shared));
    }

    //sort the activities by time and date.
    public void sortByDate() {
    }

}
