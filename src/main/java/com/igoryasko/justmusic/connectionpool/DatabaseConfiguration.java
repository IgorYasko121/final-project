package com.igoryasko.justmusic.connectionpool;

import java.util.ResourceBundle;

/**
 * The {@code Database Configuration} class reads a configuration files for the connection pool.
 * @author Igor Yasko on 2019-07-19.
 */
enum DatabaseConfiguration {
    INSTANCE;

    DatabaseConfiguration() {
    }

    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("database");
    private static final String URL = RESOURCE.getString("db.url");
    private static final String USER = RESOURCE.getString("db.user");
    private static final String PASSWORD = RESOURCE.getString("db.password");
    private static final String DRIVER = RESOURCE.getString("db.driver");
    private static final int POOL_SIZE = Integer.parseInt(RESOURCE.getString("db.poolsize"));

    public String getURL() {
        return URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getDRIVER() {
        return DRIVER;
    }

    public int getPoolSize() {
        return POOL_SIZE;
    }

}
