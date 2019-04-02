package Domæne.KalenderModul;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Kalender {

    private List<Aktivitet> calender = new ArrayList<>();

    public void deleteActivity() {
    }

    public void editActivity() {
    }

    public void createActivity(Bruger creator, String place, Date startDate, Date endDate, String description, String type, Image pictogram, Boolean shared) {
        calender.add(new Aktivitet(creator, place, startDate, endDate, description, type, pictogram, shared));
    }
    
    // check if the activities overlap eachother.
    public void checkOverlap(){
    }
    
    //sort the activities by time and date.
    public void sortByDate(){
    }

}
