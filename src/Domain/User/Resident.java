package Domain.User;

import Domain.CalendarModule.Calendar;
import Domain.DiaryModule.Diary;

public class Resident extends User {

    private Diary residentDiary;
    private Calendar residentCalendar;

    public Resident() {
        this.residentDiary = new Diary();
        this.residentCalendar = new Calendar();
    }

    public Resident(String firstName, String lastName, String username, String password) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
        super.setUsername(username);
        super.setPassword(password);

        this.residentDiary = new Diary();
        this.residentCalendar = new Calendar();
    }

    public Diary getResidentDiary() {
        return residentDiary;
    }

    public void setResidentDiary(Diary residentDiary) {
        this.residentDiary = residentDiary;
    }

    public Calendar getResidentCalendar() {
        return residentCalendar;
    }

    public void setResidentCalendar(Calendar residentCalendar) {
        this.residentCalendar = residentCalendar;
    }

}
