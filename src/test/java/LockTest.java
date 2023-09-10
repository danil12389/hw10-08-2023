import org.example.locks.LockClient;
import org.example.locks.LockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    LockRepository lockRepository;

    LockClient lockClient;

    @Test
    public void locksTest() throws InterruptedException {
        lockRepository = new LockRepository();
        lockClient = new LockClient(lockRepository);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                    lockClient.sendToRepository("tinkoff", "tinkoff.ru");
                }

        }, "Thread_ONE");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lockClient.sendToRepository("tinkoff", "tinkoff.ru/api/contactless_payments");
            }

        }, "Thread_TWO");


        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(lockRepository.getUrlByName("tinkoff"));
    }
}
