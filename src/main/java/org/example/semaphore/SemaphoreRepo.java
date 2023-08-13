package org.example.semaphore;

import java.util.HashMap;
import java.util.Map;

public class SemaphoreRepo {
    Map<String, String> repository = new HashMap<>();


    public String save(String name, String url) {
        if(!repository.containsKey(name)){
            repository.put(name, url);
            return Thread.currentThread().getName() + " wrote update";
        }
        else return Thread.currentThread().getName() + " can not write updates";
    }

    public String getUrlByName(String name) {
        return repository.get(name);
    }
}
