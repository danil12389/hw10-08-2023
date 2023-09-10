package org.example.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class LockRepository {

    Logger logger = LoggerFactory.getLogger(LockRepository.class);

    Map<String, String> repository = new HashMap<>();


    Lock lock = new ReentrantLock();


    public void save(String name, String url) {
        lock.lock();
        try {
            if(!repository.containsKey(name)){
                repository.put(name, url);
                logger.info("Map has been updated, key:value pair added {" + name + ":" + url +"}");
            }
            else {
                logger.info("Map has NOT been updated, key:value pair NOT added {" + name + ":" + url+"}");
                throw new RuntimeException("This name has been already used");
            }
        }finally {
            lock.unlock();
        }

    }

    public String getUrlByName(String name) {
        return repository.get(name);
    }

}
