package Domain.User;

import Domain.CaseModule.Case;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocialWorker extends User {

    private static int numberOfSocialWorkers = 0;
    private final int socialWorkerID;
    private final Map<Integer, Case> cases;

    public SocialWorker(){
         this.numberOfSocialWorkers++;
        this.socialWorkerID = numberOfSocialWorkers;
         cases = new HashMap<>();
    }
    public SocialWorker(String firstName, String lastName, String username, String password) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
        super.setUsername(username);
        super.setPassword(password);
        this.numberOfSocialWorkers++;
        this.socialWorkerID = numberOfSocialWorkers;
        cases = new HashMap<>();
    }

    public void createCase(String title, String caseType, String description) {
        Case newCase = new Case(title, description, caseType);
        cases.put(newCase.getCaseID(), newCase);
    }

    public void createCase(String title, String caseType, String description, Resident resident) {
        Case newCase = new Case(title, description, caseType, resident);
        cases.put(newCase.getCaseID(), newCase);
    }

    public void createCase(String title, String caseType, String description, List<File> attachedFiles) {
        Case newCase = new Case(title, description, caseType, attachedFiles);
        cases.put(newCase.getCaseID(), newCase);
    }

    public void createCase(String title, String caseType, String description, List<File> attachedFiles, Resident resident) {
        Case newCase = new Case(title, description, caseType, attachedFiles, resident);
        cases.put(newCase.getCaseID(), newCase);
    }

    public void transferCaseTo(SocialWorker socialWorker, int caseID) {
        Case caseToTransfer = this.cases.get(caseID);
        socialWorker.getCases().put(caseID, caseToTransfer);
        this.cases.remove(caseID);

    }

    public Map<Integer, Case> getCases() {
        return this.cases;
    }

    public String getName() {
        return super.getFirstName() + " " + super.getLastName();
    }

    @Override
    public String toString() {
        return this.getName() + " ID: " + this.socialWorkerID;
    }
}
