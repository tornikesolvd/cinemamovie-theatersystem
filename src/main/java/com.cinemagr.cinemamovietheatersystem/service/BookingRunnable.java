package com.cinemagr.cinemamovietheatersystem.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookingRunnable implements Runnable {
    
    private static final Logger LOGGER = LogManager.getLogger(BookingRunnable.class);
    private final String threadName;
    
    public BookingRunnable(String threadName) {
        this.threadName = threadName;
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            LOGGER.info("Runnable thread {} is executing a task: {}", threadName, i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.warn("Runnable thread {} has been interrupted", threadName);
                Thread.currentThread().interrupt();
                break;
            }
        }
        LOGGER.info("Runnable thread {} has completed", threadName);
    }
}
