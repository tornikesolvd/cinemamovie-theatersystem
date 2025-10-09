package com.cinemagr.cinemamovietheatersystem.threading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionTaskRunnable implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionTaskRunnable.class);
    private final ConnectionPool connectionPool;

    public ConnectionTaskRunnable(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void run() {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();

            connection.create("Account-" + Thread.currentThread().getName());
            Thread.sleep(500);

            String currentAccount = connection.get(0);
            LOGGER.info("[{}] Retrieved: {}", Thread.currentThread().getName(), currentAccount);
            Thread.sleep(500);

            connection.update(0, "Updated " + Thread.currentThread().getName());
            Thread.sleep(500);

            connection.delete(0);
            LOGGER.info("Account deleted");

            Thread.sleep(500);
        } catch (InterruptedException e) {
            LOGGER.error("Thread interrupted");
            Thread.currentThread().interrupt();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

}
