package Domain.CalendarModule;

import Domain.User.User;
import java.awt.Image;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Calendar {

    private Map<Integer, Activity> calender = new HashMap<>();

    public void deleteActivity(Activity activity) {
        calender.remove(activity.getId(), activity);
    }

    public void createActivity(User creator, String place, Date startDate, Date endDate, String description, String type, Image pictogram, Boolean shared) {
        Activity activity = new Activity(creator, place, startDate, endDate, description, type, pictogram, shared);
        calender.put(activity.getId(), activity);
    }

    public Map<Integer, Activity> getCalender() {
        return calender;
    }

    //sort the activities by time and date.
    public void sortByDate() {
    }

}
