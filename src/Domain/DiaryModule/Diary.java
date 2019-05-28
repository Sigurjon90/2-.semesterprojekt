package Domain.DiaryModule;

import java.util.HashMap;
import java.util.Map;

public class Diary {

    private Map<Integer, Entry> entryMap;

    public Diary() {
        entryMap = new HashMap<>();
    }

    public Map<Integer, Entry> getMap() {
        return entryMap;
    }

    public Entry getEntry(int key) {
        return entryMap.get(key);
    }

}
