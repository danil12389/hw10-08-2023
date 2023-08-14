package org.example.locks;

import java.util.HashMap;
import java.util.Map;

public class LockRepo {

    Map<String, String> repository = new HashMap<>();

    int counter = 0;


    public String save(String name, String url) {
        if(!repository.containsKey(name)){
            repository.put(name, url);
            counter++;
            return Thread.currentThread().getName() + " wrote update";
        }
        else return Thread.currentThread().getName() + " can not write updates";
    }

    public String getUrlByName(String name) {
        return repository.get(name);
    }

    public int getCounter() {
        return counter;
    }
}
