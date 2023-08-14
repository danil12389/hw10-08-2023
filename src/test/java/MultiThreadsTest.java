import org.example.locks.LockClient;
import org.example.locks.LockRepo;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiThreadsTest {

    SyncedRepo sr;
    SyncedClient sc;

    Repo r;
    Client c;

    SemaphoreClient semaphoreClient;

    SemaphoreRepo semaphoreRepo;

    LockRepo lockRepo;

    LockClient lockClient;

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


        Assertions.assertEquals(2, r.getCounter());
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
        Assertions.assertEquals(1, sr.getCounter());
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
                    semaphore.acquire();
                    Assertions.assertEquals("Thread_ONE can not write updates", semaphoreClient.sendToRepository("d", "u1"));
                    Assertions.assertEquals("u2", semaphoreRepo.getUrlByName("d"));
                    semaphore.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Thread_ONE");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();
                    semaphoreClient.sendToRepository("d", "u2");
                    semaphore.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Thread_TWO");
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        Assertions.assertEquals(1, semaphoreRepo.getCounter());

    }

    @Test
    public void locksTest() throws InterruptedException {
        lockRepo = new LockRepo();
        Lock lock = new ReentrantLock();
        lockClient = new LockClient(lockRepo, new ReentrantLock());
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                  lock.lock();
                        Assertions.assertEquals("Thread_ONE can not write updates", lockClient.sendToRepository("d", "u1"));
                        Assertions.assertEquals("u2", lockRepo.getUrlByName("d"));
                }finally{
                    lock.unlock();
                }

            }
        }, "Thread_ONE");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
               try {
                   lock.lock();
                   lockClient.sendToRepository("d", "u2");
               }finally{
                   lock.unlock();
               }
            }
        }, "Thread_TWO");
        t1.start();
        t2.start();


        t1.join();
        t2.join();

        Assertions.assertEquals(1, lockRepo.getCounter());
    }
}
