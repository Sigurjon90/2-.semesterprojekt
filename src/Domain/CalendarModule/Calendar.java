package Domain.CalendarModule;

import Domain.User.User;
import java.awt.Image;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Toggle;

public class Calendar {
    
    
    public Calendar(){
        
    }

    private Map<Integer, Activity> calender = new HashMap<>();

    public void deleteActivity(Activity activity) {
        calender.remove(activity.getId(), activity);
    }

    public void createActivity(String title, User creator, String place, String startDate, String endDate, String description, String type, Boolean shared) {
            Activity activity = new Activity(title, creator, place, startDate, endDate, description, type, shared);
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
