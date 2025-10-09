package com.cinemagr.cinemamovietheatersystem.threading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool INSTANCE;
    private final BlockingQueue<Connection> connections;

    private ConnectionPool(int connectionSize) {
        connections = new LinkedBlockingQueue<>();
        for (int i = 0; i < connectionSize; i++) {
            connections.offer(new Connection(i));
        }
        LOGGER.info("Connection pool size {}", connectionSize);
    }

    public static synchronized ConnectionPool getINSTANCE(int connectionSize) {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPool(connectionSize);
        }
        return INSTANCE;
    }

    public Connection getConnection() throws InterruptedException {
        LOGGER.info("[{}] Awaiting connection...", Thread.currentThread().getName());
        Connection connection = connections.take();
        LOGGER.info("[{}] Established connection {}", Thread.currentThread().getName(), connection);
        return connection;
    }

    public void releaseConnection(Connection connection) {
        connections.offer(connection);
        LOGGER.info("[{}] Released connection {}", Thread.currentThread().getName(), connection);
    }
}
