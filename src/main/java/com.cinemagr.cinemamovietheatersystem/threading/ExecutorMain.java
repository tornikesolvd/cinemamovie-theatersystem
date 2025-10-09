package com.cinemagr.cinemamovietheatersystem.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ExecutorMain {

    private static final Logger LOGGER = Logger.getLogger(ExecutorMain.class.getName());

    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPool.getINSTANCE(5);

        LOGGER.info("7 Threads Executor");

        ExecutorService executor = Executors.newFixedThreadPool(7);

        for (int i = 0; i < 7; i++) {
            executor.submit(new ConnectionTaskRunnable(pool));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            LOGGER.warning("Executor interrupted");
            Thread.currentThread().interrupt();
        }

        LOGGER.info("Finished all threads");
    }
}
