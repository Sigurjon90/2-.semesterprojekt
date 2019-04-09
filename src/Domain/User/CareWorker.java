package Domain.User;

import java.awt.Image;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jens
 */
public class CareWorker extends User {

    private final String firstName;
    private final String lastName;
    private final int careWorkerID;
    private static int IDCounter;

    public CareWorker(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.careWorkerID = IDCounter;
        IDCounter++;
    }

    public void createEntryForUserDiary(User Resident, Date date, String description, List<Integer> accessType, List<File> files) {
        //Resident.Diary.createEntry(date, description, accessType, files);
    }

    public void editEntryForUserDiaryEntry(User Resident, int key, String description, List<Integer> accessType, List<File> files) {
        //Resident.Diary.getEntry(key).editEntry(description, accessType, files);
    }

    public void editAccessTypeForUserDiaryEntry(User Resident, int key, List<Integer> accessType) {
        //Resident.Diary.getEntry(key).editAccessType(accessType);
    }

    public void editDescriptionForUserDiaryEntry(User Resident, int key, String description) {
        //Resident.Diary.getEntry(key).editDescription(description);
    }

    public void editFilesForUserDiaryEntry(User Resident, int key, List<File> files) {
        //Resident.Diary.getEntry(key).editFiles(files);
    }
    
    public void createActivityForUser(User Resident, User creator, String place, Date startDate, Date endDate, String description, String type, Image pictogram, boolean shared){
        //Resident.Calendar.createActivity(creator, place, startDate, endDate, description, type, pictogram, shared);
    }
    
    public void updateActivityForUser(User Resident, int key, User creator, String place, Date startDate, Date endDate, String description, String type, Image pictogram, boolean shared){
        //Resident.Calendar.getActivity(key).updateActivity(creator, place, startDate, endDate, description, type, pictogram, shared);
    }   //TODO Mangler getActivity i Calendar klassen

}
