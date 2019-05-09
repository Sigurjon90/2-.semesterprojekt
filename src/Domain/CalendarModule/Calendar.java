package Domain.CalendarModule;

import Domain.User.User;
import Persistence.ActivityManager;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Calendar {
    
    private Map<Integer, Activity> calendar;
            
    public Calendar() {
        calendar = new HashMap<>();
    }

    public void deleteActivity(Activity activity) {
        calendar.remove(activity.getActivityID(), activity);
    }

    public void createActivity(String title, User creator, String place, LocalDateTime startDate, LocalDateTime endDate, String description, String type, Boolean shared, Boolean entry) {
        Activity activity = new Activity(title, creator, place, startDate, endDate, description, type, shared, entry);
        calendar.put(activity.getActivityID(), activity);
        ActivityManager.storeActivity(activity);
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
