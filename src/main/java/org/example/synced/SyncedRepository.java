package org.example.synced;

import org.example.locks.LockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SyncedRepository {


    Logger logger = LoggerFactory.getLogger(SyncedRepository.class);

    Map<String, String> repository = new ConcurrentHashMap<>();

    public void save(String name, String url) {
        String res = repository.putIfAbsent(name, url);
        if (res == null) {
            logger.info("Map has been updated, key:value pair added {" + name + ":" + url +"}");
        }else {
            logger.info("Map has NOT been updated, key:value pair NOT added {" + name + ":" + url+"}");
            throw new RuntimeException("This name {" + res + "} has been already used");
        }
    }

    public String getUrlByName(String name) {
        return repository.get(name);
    }

}
