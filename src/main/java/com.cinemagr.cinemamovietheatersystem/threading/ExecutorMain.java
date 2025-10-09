package com.cinemagr.cinemamovietheatersystem.threading;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ExecutorMain {

    private static final Logger LOGGER = Logger.getLogger(ExecutorMain.class.getName());

    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPool.getInstance(5);

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

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    try {
                        Connection connection = pool.getConnection();
                        connection.create("Future Account");
                        String current = connection.get(0);
                        pool.releaseConnection(connection);
                        return current;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return "Interrupted";
                    }
                })
                .thenApply(current -> {
                    LOGGER.info("Compiling current results");
                    return current.toUpperCase();
                }).thenApply(formattedCurrent -> {
                    LOGGER.info("Continuing process");
                    return formattedCurrent.toUpperCase();
                });

        future.thenAccept(finalCurrent -> {
            LOGGER.info("Final Current");
            LOGGER.info(finalCurrent.toUpperCase());
        }).join();

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Connection connection = pool.getConnection();
                connection.create("Second Future Account");
                Thread.sleep(500);
                pool.releaseConnection(connection);
                LOGGER.info("Future duty completed");
                return "Result of the future executing";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Interrupted";
            }
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Connection connection = pool.getConnection();
                connection.create("Third Future Account");
                Thread.sleep(500);
                pool.releaseConnection(connection);
                LOGGER.info("Future duty completed");
                return "Result of the future executing";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Interrupted";
            }
        });

        CompletableFuture<Void> futureList = CompletableFuture.allOf(future, future2);
        futureList.thenRun(() ->
                LOGGER.info("All futures completed! ")).join();
    }

}
