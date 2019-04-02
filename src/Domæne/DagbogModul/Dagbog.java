package Domæne.DagbogModul;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dagbog {

    Map<Integer, Indlæg> entryMap = new HashMap<>();

    public Dagbog() {

    }

    public void deleteEntry(Indlæg entry) {
        entryMap.remove(entry.getId());
    }

    public Map<Integer, Indlæg> getList() {
        return entryMap;
    }
    
    public void createEntry(Date date, String description, List<Integer> accessType, List<File> files){
        Indlæg entry = new Indlæg(date, description, accessType, files);
        entryMap.put(entry.id, entry);
    }

}
