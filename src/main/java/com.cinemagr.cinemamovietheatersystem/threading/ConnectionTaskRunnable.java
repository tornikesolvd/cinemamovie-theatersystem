package com.cinemagr.cinemamovietheatersystem.threading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionTaskRunnable implements Runnable{

    private static final Logger LOGGER = LogManager.getLogger(ConnectionTaskRunnable.class);
    private final ConnectionPool connectionPool;

    public ConnectionTaskRunnable(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void run() {
        Connection connection = null;
        try{
            connection = connectionPool.getConnection();
            Thread.sleep(1000);
        } catch (InterruptedException e){
            LOGGER.error("Thread interrupted");
            Thread.currentThread().interrupt();
        } finally {
            if(connection != null){
                connectionPool.releaseConnection(connection);
            }
        }
    }

}
