package org.example.synced;

import org.example.locks.LockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SyncedRepository {

    Map<String, String> repository = new HashMap<>();

    Logger logger = LoggerFactory.getLogger(SyncedRepository.class);

    Map<String, String> sr = Collections.synchronizedMap(repository);

    public void save(String name, String url) {
        String res = sr.putIfAbsent(name, url);
        if (res == null) {
            logger.info("Map has been updated, key:value pair added {" + name + ":" + url +"}");
        }else {
            logger.info("Map has NOT been updated, key:value pair NOT added {" + name + ":" + url+"}");
            throw new RuntimeException("This name has been already used");
        }
    }

    public String getUrlByName(String name) {
        return repository.get(name);
    }

}
