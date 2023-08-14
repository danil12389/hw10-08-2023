package org.example.unsynced;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Repo {

    Map<String, String> repository = new HashMap<>();

    int counter = 0;
    public void save(String name, String url) {
        repository.put(name, url);
        counter++;
    }

    public String getUrlByName(String name) {
        return repository.get(name);
    }

    public int getCounter() {
        return counter;
    }
}
