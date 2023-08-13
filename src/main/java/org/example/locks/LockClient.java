package org.example.locks;

import org.example.unsynced.Repo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockClient {
    LockRepo r;
    Lock lock;

    public LockClient(LockRepo r, Lock lock) {
        this.r = r;
        this.lock = new ReentrantLock();
    }

    public String sendToRepository(String name, String url) {
        return r.save(name, url);
    }
}
