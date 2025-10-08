package com.cinemagr.cinemamovietheatersystem.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookingThread extends Thread {
    
    private static final Logger LOGGER = LogManager.getLogger(BookingThread.class);
    private final String threadName;
    
    public BookingThread(String threadName) {
        this.threadName = threadName;
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            LOGGER.info("Thread class {} is executing task: {}", threadName, i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.warn("Thread class {} has been interrupted", threadName);
                Thread.currentThread().interrupt();
                break;
            }
        }
        LOGGER.info("Thread class {} has completed", threadName);
    }
}
