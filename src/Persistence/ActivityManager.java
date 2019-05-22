package Persistence;

import Domain.CalendarModule.Activity;
import Domain.CalendarModule.Calendar;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ActivityManager {

    private static String place;
    private static String creator;
    private static String description;
    private static String type;
    private static String startDate;
    private static String endDate;
    private static Boolean shared;
    private static Boolean entry;
    private static String title;

    private static int residentID;

    private static void getActivityInfo(Activity activity) {
        place = activity.getPlace();
        creator = activity.getCreator();
        description = activity.getDescription();
        type = activity.getType();
        startDate = activity.getStartTimeAndDate();
        endDate = activity.getEndTimeAndDate();
        shared = activity.getShared();
        entry = activity.getEntry();
        title = activity.getTitle();
    }

    private static void getResidentID() {
        residentID = UserManager.getCurrentResident().getID();
    }

    public static int storeActivity(Activity activity) throws SQLException {
        ActivityManager.getActivityInfo(activity);
        ActivityManager.getResidentID();
        System.out.println(startDate);
        if (ActivityRepository.storeActivity(place, description, type, startDate, endDate, shared, entry, title, residentID, creator)) {
            return ActivityRepository.getHighestID();
        }
        return 0;
    }

    public static void getActivities(int residentID) throws SQLException {

        ArrayList<Integer> activities = ActivityRepository.getActivityIDs(residentID);
        for (Integer id : activities) {
            ArrayList<Object> activityInfo = ActivityRepository.getActivityInfo(id);
            Calendar.getCurrentCalendar().putInCalendar(new Activity((String) activityInfo.get(0), (String) activityInfo.get(1), (String) activityInfo.get(2), getLocalDateTime((String) activityInfo.get(3)), getLocalDateTime((String) activityInfo.get(4)), (String) activityInfo.get(5), (String) activityInfo.get(6), (Boolean) activityInfo.get(7), (Boolean) activityInfo.get(8), (int) (activityInfo.get(10))));
        }
    }

    private static LocalDateTime getLocalDateTime(String date) {
        return LocalDateTime.parse(date);
    }
    
    public static void deleteActivity(int id) throws SQLException {
        ActivityRepository.deleteActivity(id);
    }

}
