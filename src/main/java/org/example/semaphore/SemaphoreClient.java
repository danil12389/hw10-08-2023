package org.example.semaphore;

import org.example.unsynced.Repo;

import java.util.concurrent.Semaphore;

public class SemaphoreClient {
    SemaphoreRepo r;

    Semaphore s;

    public SemaphoreClient(SemaphoreRepo r, Semaphore s) {
        this.r = r;
        this.s = s;
    }
    public String sendToRepository(String name, String url) throws InterruptedException {
        s.acquire();
        String res = r.save(name, url);
        s.release();
        return res;
    }
}
