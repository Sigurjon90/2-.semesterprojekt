package Domæne.SagModul;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SagsBehandler {

    private static int numberOfSocialWorkers = 0;
    private String name;
    private int socialWorkerID;
    private Map<Integer, Sag> cases;

    public SagsBehandler(String name) {
        this.name = name;
        this.numberOfSocialWorkers++;
        this.socialWorkerID = numberOfSocialWorkers;
        cases = new HashMap<>();
    }

    public String createCase(String caseType, String description) {
        Sag newCase = new Sag(description, caseType);
        cases.put(newCase.getCaseID(), newCase);
        return "A case with the ID: " + newCase.getCaseID() + " has been successfully created \n";

    }

    public String createCase(String caseType, String description, List<File> attachedFiles) {
        Sag newCase = new Sag(description, caseType, attachedFiles);
        cases.put(newCase.getCaseID(), newCase);
        return "A case with the ID: " + newCase.getCaseID() + " has been successfully created \n";
    }

    public void deleteCase(Sag caseToDelete) {
        int caseID = caseToDelete.getCaseID();
        caseToDelete.decrementCases();
        cases.remove(caseID);
        System.out.println("The Case with ID: " + caseID + " has been successfully deleted \n");
    }

    public void transferCaseTo(SagsBehandler socialWorker, int caseID) {
        Sag caseToTransfer = this.cases.get(caseID);
        socialWorker.getCases().put(caseID, caseToTransfer);
        this.cases.remove(caseID);
        System.out.println("Case with ID: " + caseID + " has been successfullt trasnfered from " + this.name + " to " + socialWorker.getName() + "\n");

    }

    public Map<Integer, Sag> getCases() {
        return this.cases;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name + " ID: " + this.socialWorkerID;
    }
}
