package com.example.oodcw;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceManager {
    //Executor service with fixed thread pool
    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    //Shuts down the executor service
    public static void shutdownServices() {
        executorService.shutdown();
    }
}
