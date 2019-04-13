package Domain.CalendarModule;

import Domain.User.User;
import java.awt.Image;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Toggle;

public class Calendar {

    public Calendar() {

    }

    private Map<Integer, Activity> calender = new HashMap<>();

    public void deleteActivity(Activity activity) {
        calender.remove(activity.getId(), activity);
    }

    public void createActivity(String title, User creator, String place, LocalDateTime startDate, LocalDateTime endDate, String description, String type, Boolean shared, Boolean entry) {
        Activity activity = new Activity(title, creator, place, startDate, endDate, description, type, shared, entry);
        calender.put(activity.getId(), activity);
    }

    public Map<Integer, Activity> getCalender() {
        return calender;
    }

    public Activity getActivity(int key) {
        return calender.get(key);
    }

    //sort the activities by time and date.
    public void sortByDate() {
    }

}
