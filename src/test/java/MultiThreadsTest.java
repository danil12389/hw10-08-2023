import org.example.Client;
import org.example.Repo;
import org.example.SyncedClient;
import org.example.SyncedRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiThreadsTest {

    SyncedRepo sr;
    SyncedClient sc;

    Repo r;
    Client c;

    @Test
    public void shouldReturnFirstUrl() throws InterruptedException {
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
    public void shouldReturnSecondUrl() throws InterruptedException {
        sr = new SyncedRepo();
        sc = new SyncedClient(sr);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Exception exception = assertThrows(RuntimeException.class, () -> {
                    sc.sendToRepository("d", "u1");
                });

                String expectedMessage = "Name {d} already used";
                String actualMessage = exception.getMessage();

                assertTrue(actualMessage.contains(expectedMessage));
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
    }
}
