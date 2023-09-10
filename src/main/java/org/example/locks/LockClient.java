package org.example.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockClient {
    LockRepository lockRepository;


    public LockClient(LockRepository lockRepository) {
        this.lockRepository = lockRepository;
    }

    public void sendToRepository(String name, String url) {
         lockRepository.save(name, url);
    }
}
