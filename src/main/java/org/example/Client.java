package org.example;

public class Client {

    Repo r;

    public Client(Repo r) {
        this.r = r;
    }

    public void sendToRepository(String name, String url) {
        r.save(name, url);
    }
}
