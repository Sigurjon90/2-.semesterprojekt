package Domain.DiaryModule;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Diary {

    Map<Integer, Entry> entryMap = new HashMap<>();

    public Diary() {

    }

    public void deleteEntry(Entry entry) {
        entryMap.remove(entry.getId());
    }

    public Map<Integer, Entry> getList() {
        return entryMap;
    }
    
    public void createEntry(Date date, String description, List<Integer> accessType, List<File> files){
        Entry entry = new Entry(date, description, accessType, files);
        entryMap.put(entry.id, entry);
    }
    
    public Entry getEntry(int key){
        return entryMap.get(key);
    }
}
