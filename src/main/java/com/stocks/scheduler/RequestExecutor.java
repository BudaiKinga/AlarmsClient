package com.stocks.scheduler;

import java.util.Arrays;
import java.util.concurrent.*;

public class RequestExecutor {
    // configure from file?
    public static int CORE_POOL_SIZE = 5;
    public static int INITIAL_DELAY = 0;
    public static int PERIOD = 5;

    public RequestExecutor(TimeUnit timeUnit) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(
                new RequestService(Arrays.asList("MSTF")), INITIAL_DELAY, PERIOD, timeUnit);
    }

    public static void main(String[] args) {
        RequestExecutor exec = new RequestExecutor(TimeUnit.SECONDS);
    }
}
