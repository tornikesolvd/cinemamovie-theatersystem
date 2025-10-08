package com.cinemagr.cinemamovietheatersystem.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {
    
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static volatile ConnectionPool instance;
    private static final Object lock = new Object();
    
    private final BlockingQueue<MockConnection> availableConnections;
    private final ConcurrentHashMap<String, MockConnection> activeConnections;
    private final AtomicInteger totalConnections;
    private final int maxPoolSize;
    private final int initialPoolSize;
    
    private ConnectionPool(int maxPoolSize, int initialPoolSize) {
        this.maxPoolSize = maxPoolSize;
        this.initialPoolSize = initialPoolSize;
        this.availableConnections = new LinkedBlockingQueue<>();
        this.activeConnections = new ConcurrentHashMap<>();
        this.totalConnections = new AtomicInteger(0);
        
        LOGGER.info("ConnectionPool initialized with max size: {}, initial size: {}", maxPoolSize, initialPoolSize);
    }
    
    public static ConnectionPool getInstance() {
        return getInstance(10, 5);
    }
    
    public static ConnectionPool getInstance(int maxPoolSize, int initialPoolSize) {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ConnectionPool(maxPoolSize, initialPoolSize);
                }
            }
        }
        return instance;
    }
    
    public MockConnection getConnection() throws InterruptedException {
        MockConnection connection = availableConnections.poll();
        
        if (connection == null) {
            if (totalConnections.get() < maxPoolSize) {
                connection = createNewConnection();
            } else {
                LOGGER.warn("Pool is full, waiting for available connection");
                connection = availableConnections.take();
            }
        }
        
        activeConnections.put(connection.getConnectionId(), connection);
        LOGGER.info("Connection {} obtained from pool. Active: {}, Available: {}", 
                   connection.getConnectionId(), activeConnections.size(), availableConnections.size());
        
        return connection;
    }
    
    public void returnConnection(MockConnection connection) {
        if (connection == null || !connection.isActive()) {
            LOGGER.warn("Attempting to return invalid connection: {}", connection);
            return;
        }
        
        activeConnections.remove(connection.getConnectionId());
        
        if (availableConnections.size() < initialPoolSize) {
            availableConnections.offer(connection);
            LOGGER.info("Connection {} returned to pool. Active: {}, Available: {}", 
                       connection.getConnectionId(), activeConnections.size(), availableConnections.size());
        } else {
            connection.close();
            totalConnections.decrementAndGet();
            LOGGER.info("Connection {} closed due to pool size limit. Total connections: {}", 
                       connection.getConnectionId(), totalConnections.get());
        }
    }
    
    private MockConnection createNewConnection() {
        MockConnection connection = new MockConnection();
        totalConnections.incrementAndGet();
        LOGGER.debug("New connection created: {}", connection.getConnectionId());
        return connection;
    }
    
    public void closePool() {
        LOGGER.info("Closing connection pool");
        
        for (MockConnection connection : activeConnections.values()) {
            connection.close();
        }
        activeConnections.clear();
        
        MockConnection connection;
        while ((connection = availableConnections.poll()) != null) {
            connection.close();
        }
        
        totalConnections.set(0);
        LOGGER.info("Connection pool closed");
    }
    
    public int getActiveConnectionsCount() {
        return activeConnections.size();
    }
    
    public int getAvailableConnectionsCount() {
        return availableConnections.size();
    }
    
    public int getTotalConnectionsCount() {
        return totalConnections.get();
    }
    
    public boolean isPoolFull() {
        return totalConnections.get() >= maxPoolSize;
    }
}
