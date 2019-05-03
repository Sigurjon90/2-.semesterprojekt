package Domain.User;

public class CareWorker extends User {

    private final int careWorkerID;
    private static int idCounter;
    
    public CareWorker(String firstName, String lastName) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
        this.careWorkerID = idCounter;
        idCounter++;
    }
    
}
