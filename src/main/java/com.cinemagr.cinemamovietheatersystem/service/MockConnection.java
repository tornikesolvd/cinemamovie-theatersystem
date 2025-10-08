package com.cinemagr.cinemamovietheatersystem.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.UUID;

public class MockConnection {
    
    private static final Logger LOGGER = LogManager.getLogger(MockConnection.class);
    
    private final String connectionId;
    private final LocalDateTime createdTime;
    private boolean isActive;
    private boolean isClosed;
    
    public MockConnection() {
        this.connectionId = UUID.randomUUID().toString();
        this.createdTime = LocalDateTime.now();
        this.isActive = true;
        this.isClosed = false;
        LOGGER.debug("MockConnection created with ID: {}", connectionId);
    }
    
    public void executeQuery(String query) {
        if (isClosed) {
            throw new IllegalStateException("Connection is closed");
        }
        LOGGER.info("Executing query on connection {}: {}", connectionId, query);
    }
    
    public void close() {
        if (!isClosed) {
            this.isClosed = true;
            this.isActive = false;
            LOGGER.info("Connection {} closed", connectionId);
        }
    }
    
    public boolean isActive() {
        return isActive && !isClosed;
    }
    
    public String getConnectionId() {
        return connectionId;
    }
    
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    
    @Override
    public String toString() {
        return "MockConnection{id='" + connectionId + "', active=" + isActive + ", closed=" + isClosed + "}";
    }
}
