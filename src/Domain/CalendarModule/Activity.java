package Domain.CalendarModule;

import Persistence.ActivityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import UI.Vault;
import java.sql.SQLException;

public class Activity {

    private String place;
    private String description;
    private String type;
    private LocalDateTime endDate;
    private LocalDateTime startDate;
    private Boolean shared;
    private String creator;
    private Boolean entry;
    private String title;
    private int activityID;

    public Activity(String title, String creator, String place, LocalDateTime startDate, LocalDateTime endDate, String description, String type, Boolean shared, Boolean entry, int activityID) {
        this.title = title;
        this.creator = creator;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.type = type;
        this.shared = shared;
        this.activityID = activityID;
        this.entry = entry;
    }

    public Activity(String title, String creator, String place, LocalDateTime startDate, LocalDateTime endDate, String description, String type, Boolean shared, Boolean entry) {
        this.title = title;
        this.creator = creator;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.type = type;
        this.shared = shared;
        this.entry = entry;
    }

    public void updateActivity(String title, String creator, String place, LocalDateTime startDate, LocalDateTime endDate, String description, String type, Boolean shared, Boolean entry) throws SQLException {
        this.title = title;
        this.creator = creator;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.type = type;
        this.shared = shared;
        this.entry = entry;
        ActivityManager.deleteActivity(Vault.currentActivity.getActivityID());
        ActivityManager.storeActivity(this);
    }

    public String getCreator() {
        return creator;
    }

    public String getPlace() {
        return place;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getStartTimeAndDate() {
        return startDate.toString();
    }

    public String getEndTimeAndDate() {
        return endDate.toString();
    }

    public LocalDate getEndDate() {
        return endDate.toLocalDate();
    }

    public LocalDate getStartDate() {
        return startDate.toLocalDate();
    }

    public LocalTime getEndTime() {
        return endDate.toLocalTime();
    }

    public LocalTime getStartTime() {
        return startDate.toLocalTime();
    }

    public Boolean getShared() {
        return shared;
    }

    public String getTitle() {
        return title;
    }

    public int getActivityID() {
        return activityID;
    }

    public Boolean getEntry() {
        return entry;
    }

    public int getDay() {
        return this.startDate.getDayOfWeek().getValue();
    }

    public String getInfo() {

        return String.format("%02d", startDate.getHour()) + ":" + String.format("%02d", startDate.getMinute()) + " - " + String.format("%02d", endDate.getHour()) + ":" + String.format("%02d", endDate.getMinute()) + " : " + title;
    }

    @Override
    public String toString() {

        return String.format("%02d", startDate.getHour()) + ":" + String.format("%02d", startDate.getMinute()) + " - " + String.format("%02d", endDate.getHour()) + ":" + String.format("%02d", endDate.getMinute()) + " : " + title;

    }

}
