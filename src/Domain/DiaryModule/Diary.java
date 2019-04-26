package Domain.DiaryModule;

import java.util.HashMap;
import java.util.Map;

public class Diary {

    Map<Integer, Entry> entryMap;

    public Diary() {
        entryMap = new HashMap<>();
    }

    public void deleteEntry(Entry entry) {
        entryMap.remove(entry.getEntryID());
    }

    public Map<Integer, Entry> getMap() {
        return entryMap;
    }
    
//    public void createEntry(LocalDate date, String description, List<File> files){
//        Entry entry = new Entry(date, description, files);
//        entryMap.put(entry.getEntryID(), entry);
//    }
    
    public Entry getEntry(int key){
        return entryMap.get(key);
    }
    
    
    
}
