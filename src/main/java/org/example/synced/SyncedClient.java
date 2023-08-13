package org.example.synced;

import org.example.synced.SyncedRepo;

public class SyncedClient {
    SyncedRepo r;

    public SyncedClient(SyncedRepo r) {
        this.r = r;
    }

    public void sendToRepository(String name, String url) {
         r.save(name, url);
    }
    }
