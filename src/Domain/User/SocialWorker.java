package Domain.User;

import Domain.CaseModule.Case;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocialWorker extends User{

    private static int numberOfSocialWorkers = 0;
    private final int socialWorkerID;
    private final Map<Integer, Case> cases;

    public SocialWorker(String firstName, String lastName, String username, String password) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
        super.setUsername(username);
        super.setPassword(password);
        this.numberOfSocialWorkers++;
        this.socialWorkerID = numberOfSocialWorkers;
        cases = new HashMap<>();
    }

    public String createCase(String caseType, String description) {
        Case newCase = new Case(description, caseType);
        cases.put(newCase.getCaseID(), newCase);
        return "A case with the ID: " + newCase.getCaseID() + " has been successfully created \n";

    }

    public String createCase(String caseType, String description, List<File> attachedFiles) {
        Case newCase = new Case(description, caseType, attachedFiles);
        cases.put(newCase.getCaseID(), newCase);
        return "A case with the ID: " + newCase.getCaseID() + " has been successfully created \n";
    }

    public void deleteCase(Case caseToDelete) {
        int caseID = caseToDelete.getCaseID();
        caseToDelete.decrementCases();
        cases.remove(caseID);
        System.out.println("The Case with ID: " + caseID + " has been successfully deleted \n");
    }

    public void transferCaseTo(SocialWorker socialWorker, int caseID) {
        Case caseToTransfer = this.cases.get(caseID);
        socialWorker.getCases().put(caseID, caseToTransfer);
        this.cases.remove(caseID);
        System.out.println("Case with ID: " + caseID + " has been successfully transfered from "  + this.getName() + " to " + socialWorker.getName() + "\n");

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
