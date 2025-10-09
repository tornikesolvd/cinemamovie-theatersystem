package com.cinemagr.cinemamovietheatersystem.threading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionTask extends Thread{

    private static final Logger LOGGER = LogManager.getLogger(ConnectionTask.class);

    private ConnectionPool connectionPool;

    public ConnectionTask(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }

    @Override
    public void run(){
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
