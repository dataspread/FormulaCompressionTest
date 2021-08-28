package utils;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleThreadPoolExecutor {

    private static final int DEFAULT_QUEUE_SIZE = 1000;

    public static ExecutorService getNewExecutor (int poolSize) {
        return SimpleThreadPoolExecutor.getNewExecutor(poolSize, DEFAULT_QUEUE_SIZE);
    }

    public static ExecutorService getNewExecutor (int poolSize, int queueSize) {
        return new ThreadPoolExecutor(
            poolSize
            , poolSize
            , 30
            , TimeUnit.SECONDS
            , new ArrayBlockingQueue<Runnable>(queueSize)
            , new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    public static void wait (ExecutorService executor) {
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}

