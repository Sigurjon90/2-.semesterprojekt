package Domain.CalendarModule;

import Domain.User.User;
import Persistence.ActivityManager;
import Persistence.UserManager;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Calendar {

    private static Calendar currentCalendar;

    private Map<Integer, Activity> calendar;
    private User resident;

    public Calendar(User resident) {
        this.resident = resident;
        calendar = new HashMap<>();
    }

    public static Calendar getCurrentCalendar() {
        return currentCalendar;
    }

    public static void enableCurrentCalendar() {
        currentCalendar = new Calendar(UserManager.getCurrentResident());
    }

    public void createActivity(String title, String creator, String place, LocalDateTime startDate, LocalDateTime endDate, String description, String type, Boolean shared, Boolean entry) {
        Activity activity = new Activity(title, creator, place, startDate, endDate, description, type, shared, entry);
        int newID = ActivityManager.storeActivity(activity);
        calendar.put(newID, activity);
        System.out.println(startDate.toString());

    }

    public void putInCalendar(Activity activity) {
        calendar.put(activity.getActivityID(), activity);
    }

    public Map<Integer, Activity> getCalendar() {
        return calendar;
    }

    public Activity getActivity(int key) {
        return calendar.get(key);
    }

    public Set<Integer> getKeySet() {
        return calendar.keySet();
    }
}
