package org.example.synced;

public class SyncedClient {
    SyncedRepository r;

    public SyncedClient(SyncedRepository r) {
        this.r = r;
    }

    public void sendToRepository(String name, String url) {
         r.save(name, url);
    }
    }
