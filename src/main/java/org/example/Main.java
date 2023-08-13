    package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Repo r = new Repo();
        Client client = new Client(r);
        //SyncedRepo r = new SyncedRepo();
       // SyncedClient client = new SyncedClient(r);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                client.sendToRepository("d", "u1");
            }
        }, "Thread_ONE");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                client.sendToRepository("d", "u2");
            }
        }, "Thread_TWO");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(r.getUrlByName("d"));
    }
}