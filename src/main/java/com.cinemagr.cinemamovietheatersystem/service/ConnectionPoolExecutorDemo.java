package com.cinemagr.cinemamovietheatersystem.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConnectionPoolExecutorDemo {
    
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPoolExecutorDemo.class);
    
    public static void main(String[] args) {
        LOGGER.info("Starting Connection Pool ExecutorService Demo");
        
        ConnectionPool pool = ConnectionPool.getInstance(5, 5);
        ExecutorService executor = Executors.newFixedThreadPool(7);
        
        List<Future<?>> futures = new ArrayList<>();
        
        LOGGER.info("Submitting 7 tasks with 5 available connections");
        LOGGER.info("5 tasks should get connections immediately, 2 tasks should wait");
        
        for (int i = 1; i <= 7; i++) {
            String taskName = "ExecutorTask-" + i;
            Future<?> future = executor.submit(new ExecutorConnectionWorker(taskName, pool));
            futures.add(future);
        }
        
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                LOGGER.error("Task execution failed", e);
            }
        }
        
        LOGGER.info("All tasks completed");
        LOGGER.info("Final pool status - Active: {}, Available: {}, Total: {}", 
                   pool.getActiveConnectionsCount(), pool.getAvailableConnectionsCount(), pool.getTotalConnectionsCount());
        
        executor.shutdown();
        LOGGER.info("ExecutorService shutdown");
        
        pool.closePool();
        LOGGER.info("Connection pool closed");
    }
    
    private static class ExecutorConnectionWorker implements Runnable {
        
        private static final Logger LOGGER = LogManager.getLogger(ExecutorConnectionWorker.class);
        private final String taskName;
        private final ConnectionPool pool;
        
        public ExecutorConnectionWorker(String taskName, ConnectionPool pool) {
            this.taskName = taskName;
            this.pool = pool;
        }
        
        @Override
        public void run() {
            MockConnection connection = null;
            try {
                LOGGER.info("{} requesting connection", taskName);
                connection = pool.getConnection();
                LOGGER.info("{} obtained connection: {}", taskName, connection.getConnectionId());
                
                connection.executeQuery("SELECT movie_id, title FROM movies ORDER BY rating DESC");
                Thread.sleep(1500);
                connection.executeQuery("DELETE FROM expired_bookings WHERE created_date < NOW() - INTERVAL 30 DAY");
                Thread.sleep(1000);
                connection.executeQuery("UPDATE theater_halls SET maintenance_status = 'clean' WHERE hall_id = 1");
                Thread.sleep(500);
                connection.executeQuery("SELECT COUNT(*) as total_bookings FROM bookings WHERE screening_date = CURDATE()");
                
                LOGGER.info("{} completed all database operations", taskName);
                
            } catch (InterruptedException e) {
                LOGGER.warn("{} interrupted while working", taskName);
                Thread.currentThread().interrupt();
            } finally {
                if (connection != null) {
                    LOGGER.info("{} returning connection to pool", taskName);
                    pool.returnConnection(connection);
                }
            }
        }
    }
}
