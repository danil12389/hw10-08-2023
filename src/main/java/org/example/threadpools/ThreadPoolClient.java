package org.example.threadpools;

public class ThreadPoolClient {

    ThreadPoolRepo threadPoolRepo;

    public ThreadPoolClient(ThreadPoolRepo threadPoolRepo) {
        this.threadPoolRepo = threadPoolRepo;
    }
    public String sendToRepository(String name, String url) throws InterruptedException {
        String res = threadPoolRepo.save(name, url);
        return res;
    }
}
