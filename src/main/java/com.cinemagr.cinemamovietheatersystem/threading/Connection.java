package com.cinemagr.cinemamovietheatersystem.threading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Connection {

    private static final Logger LOGGER = LogManager.getLogger(Connection.class);
    private final int connectionId;
    private final List<String> database = new ArrayList<>();

    public Connection(int connectionId) {
        this.connectionId = connectionId;
    }

    public void create(String account){
        LOGGER.info("Creating {} connection with account {} ",  connectionId, account);
        database.add(account);
    }

    public String get(int index){
        LOGGER.info("Connection {} get {}", connectionId, index);
        return index <  database.size() ? database.get(index) : null;
    }

    public void update (int index, String account){
        LOGGER.info("Updating {} connection with account {} ",  connectionId, account);
        if(index < database.size()){
            database.set(index,account);
        }
    }

    public void delete(int index){
        LOGGER.info("Connection {} deleting {}", connectionId, index);
        if (index < database.size()) {
            database.remove(index);
        }
    }

    @Override
    public String toString(){
        return "Connection " + connectionId;
    }
}
