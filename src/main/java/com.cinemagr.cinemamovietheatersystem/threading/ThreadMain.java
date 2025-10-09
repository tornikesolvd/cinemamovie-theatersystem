package com.cinemagr.cinemamovietheatersystem.threading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadMain {

    private static final Logger LOGGER = LogManager.getLogger(ThreadMain.class);

    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPool.getINSTANCE(5);
        LOGGER.info("Connection pool is ready");

        Thread t1 = new Thread(new ConnectionTaskRunnable(pool));
        Thread t2 = new ConnectionTask(pool);

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            LOGGER.info("Thread interrupted");
            Thread.currentThread().interrupt();
        }

        Thread[] threadsList = new Thread[7];
        for (int i = 0; i < threadsList.length; i++) {
            threadsList[i] = new Thread(new ConnectionTaskRunnable(pool));
            threadsList[i].start();
        }

        for (Thread thread : threadsList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                LOGGER.info("Thread interrupted");
                throw new RuntimeException(e);
            }
        }

        LOGGER.info("Finished");

    }

}
