package org.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SyncedRepo {

    Map<String, String> repository = new HashMap<>();

    Map<String, String> sr = Collections.synchronizedMap(repository);

    public  void save(String name, String url) {
            String res = sr.putIfAbsent(name, url);
            if(!(res == null)) {
                throw new RuntimeException("Name {" + name + "} already used\n" + "Current Thread: " + Thread.currentThread().getName());
            }
    }

    public String getUrlByName(String name) {
        return repository.get(name);
    }
}
