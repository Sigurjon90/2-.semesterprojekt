/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.User;

import Domain.CalendarModule.Calendar;
import Domain.DiaryModule.Diary;

/**
 *
 * @author alext
 */
public class Resident extends User {
    
    private String institution;
    private Diary residentDiary;
    private Calendar residentCalendar;
    
    public Resident (){
        this.residentDiary = new Diary();
        this.residentCalendar = new Calendar();
    }
    
    public Resident (String firstName, String lastName, String username, String password, String institution){
        super.setFirstName(firstName);
        super.setLastName(lastName);
        super.setUsername(username);
        super.setPassword(password);
        this.institution = institution;
        this.residentDiary = new Diary();
        this.residentCalendar = new Calendar();
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
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
