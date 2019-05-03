package Domain.User;

public class CareWorker extends User {

    private final int careWorkerID;
    private static int idCounter;
    int roleid = 1;
public CareWorker(){
      this.careWorkerID = idCounter;
        idCounter++;
}
    public CareWorker(String firstName, String lastName) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
        this.careWorkerID = idCounter;
        idCounter++;
    }
    
    public int getRoleid(){
        return roleid;
    }
}
