package org.example.synced;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SyncedRepo {

    Map<String, String> repository = new HashMap<>();

    Map<String, String> sr = Collections.synchronizedMap(repository);

    public void save(String name, String url) {
           sr.putIfAbsent(name, url);
    }

    public String getUrlByName(String name) {
        return repository.get(name);
    }
}
