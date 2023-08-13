package org.example;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Repo {

    Map<String, String> repository = new HashMap<>();


    public void save(String name, String url) {
           repository.put(name, url);
    }

    public String getUrlByName(String name) {
        return repository.get(name);
    }
}
