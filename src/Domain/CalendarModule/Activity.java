package Domain.CalendarModule;

import Domain.User.User;
import java.awt.Image;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Activity {

    private String place;
    private String description;
    private String type;
    private Image pictogram = null;
    private LocalDateTime endDate;
    private LocalDateTime startDate;
    private Boolean shared;
    private User creator;
    private Boolean entry;
    private String title;
    private static int idCounter = 1;
    private int id;

    public Activity() {
    }

    public Activity(String title, User creator, String place, LocalDateTime startDate, LocalDateTime endDate, String description, String type, Boolean shared, Boolean entry) {
        this.title = title;
        this.creator = creator;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.type = type;
        this.shared = shared;
        this.id = idCounter;
        this.entry = entry;
        idCounter++;
    }

    public void updateActivity(String title, User creator, String place, LocalDateTime startDate, LocalDateTime endDate, String description, String type, Boolean shared, Boolean entry) {
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

    public String getPlace() {
        return place;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
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

    public int getId() {
        return id;
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