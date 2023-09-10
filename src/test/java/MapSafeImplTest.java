import org.example.locks.LockClient;
import org.example.locks.LockRepository;
import org.example.synced.SyncedClient;
import org.example.synced.SyncedRepository;
import org.junit.jupiter.api.Test;

public class MapSafeImplTest {

    SyncedClient syncedClient;

    SyncedRepository syncedRepository;


    @Test
    public void mapSafeTest() throws InterruptedException {
        syncedRepository = new SyncedRepository();
        syncedClient = new SyncedClient(syncedRepository);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                syncedClient.sendToRepository("tinkoff", "tinkoff.ru");
            }

        }, "Thread_ONE");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                syncedClient.sendToRepository("tinkoff", "tinkoff.ru/api/contactless_payments");
            }

        }, "Thread_TWO");


        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(syncedRepository.getUrlByName("tinkoff"));
    }

}
