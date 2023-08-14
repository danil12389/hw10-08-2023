package org.example.synced;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SyncedRepo {

    Map<String, String> repository = new HashMap<>();

    Map<String, String> sr = Collections.synchronizedMap(repository);

    int counter = 0;

    public void save(String name, String url) {
        String res = sr.putIfAbsent(name, url);
        if(res == null){
            counter++;
        }
    }

    public String getUrlByName(String name) {
        return repository.get(name);
    }

    public int getCounter() {
        return counter;
    }
}
