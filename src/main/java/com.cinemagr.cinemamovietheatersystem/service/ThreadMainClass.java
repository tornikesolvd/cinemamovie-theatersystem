package com.cinemagr.cinemamovietheatersystem.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadMainClass {
    
    private static final Logger LOGGER = LogManager.getLogger(ThreadMainClass.class);
    
    public static void main(String[] args) {
        LOGGER.info("Starting Threads Declaring");
        
        BookingRunnable runnable1 = new BookingRunnable("Booking runnable1");
        BookingRunnable runnable2 = new BookingRunnable("Booking runnable2 ");
        
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        
        BookingThread thread3 = new BookingThread("Booking thread3");
        BookingThread thread4 = new BookingThread("Booking thread4");
        
        LOGGER.info("Executing threads");
        
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            LOGGER.error("Thread has been interrupted", e);
            Thread.currentThread().interrupt();
        }
        
        LOGGER.info("All of the threads has been completed");
    }
}
