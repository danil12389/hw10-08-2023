package org.example.threadpools;

import java.util.concurrent.Callable;

public class CustomCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        return null;
    }

    public Object call(ThreadPoolClient threadPoolClient, String name, String url) throws Exception {
        threadPoolClient.sendToRepository(name, url);
        return null;
    }

}
