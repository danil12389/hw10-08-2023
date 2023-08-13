import org.example.semaphore.SemaphoreClient;
import org.example.semaphore.SemaphoreRepo;
import org.example.unsynced.Client;
import org.example.unsynced.Repo;
import org.example.synced.SyncedClient;
import org.example.synced.SyncedRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiThreadsTest {

    SyncedRepo sr;
    SyncedClient sc;

    Repo r;
    Client c;

    SemaphoreClient semaphoreClient;

    SemaphoreRepo semaphoreRepo;

    @Test
    public void unsyncedTest() throws InterruptedException {
        r = new Repo();
        c = new Client(r);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                c.sendToRepository("d", "u1");
            }
        }, "Thread_ONE");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                c.sendToRepository("d", "u2");
            }
        }, "Thread_TWO");
        t1.start();
        t2.start();

        t1.join();
        t2.join();


        Assertions.assertEquals("u1", r.getUrlByName("d"));
    }

    @Test
    public void syncedTest() throws InterruptedException {
        sr = new SyncedRepo();
        sc = new SyncedClient(sr);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    sc.sendToRepository("d", "u1");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Thread_ONE");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                sc.sendToRepository("d", "u2");
            }
        }, "Thread_TWO");
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        Assertions.assertEquals("u2", sr.getUrlByName("d"));
    }


    @Test
    public void semaphoreTest() throws InterruptedException {
        semaphoreRepo = new SemaphoreRepo();
        Semaphore semaphore = new Semaphore(1);
        semaphoreClient = new SemaphoreClient(semaphoreRepo, semaphore);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Assertions.assertEquals("Thread_ONE can not write updates", semaphoreClient.sendToRepository("d", "u1"));
                    Assertions.assertEquals("u2", semaphoreRepo.getUrlByName("d"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Thread_ONE");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphoreClient.sendToRepository("d", "u2");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Thread_TWO");
        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
