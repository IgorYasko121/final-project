package com.igoryasko.justmusic.connectionpool;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The class {@code ConnectionPool} this provides access to the database.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class ConnectionPool {

    private static ConnectionPool instance;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean isCreate = new AtomicBoolean(false);
    private DatabaseConfiguration dbConfiguration = DatabaseConfiguration.INSTANCE;
    private BlockingQueue<ProxyConnection> availableConnections;

    private ConnectionPool() {
        init();
    }

    /**
     *
     * @return instance of the class ConnectionPool.
     */
    public static ConnectionPool getInstance() {
        if (!isCreate.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isCreate.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * Initialize connection pool.
     */
    private void init() {
        try {
            Class.forName(dbConfiguration.getDRIVER());
        } catch (ClassNotFoundException e) {
            log.fatal("Driver registration error", e);
            throw new RuntimeException("Database driver connection failed", e);
        }
        availableConnections = new LinkedBlockingQueue(dbConfiguration.getPoolSize());
        for (int i = 0; i < dbConfiguration.getPoolSize(); i++) {
            ProxyConnection connection;
            try {
                connection = new ProxyConnection(createConnection());
                availableConnections.put(connection);
                log.trace("Connection is put to pool");
            } catch (SQLException | InterruptedException e) {
                log.error("Connection couldn't create", e);
            }
        }
        //todo
        checkAvailableConnections();
    }

    /**
     * Create a connection to the database
     * @return connection
     * @throws SQLException if connection can't create
     */
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(dbConfiguration.getURL(),
                dbConfiguration.getUSER(), dbConfiguration.getPASSWORD());
    }

    /**
     * Checks the difference between available connections and parameter poolSize
     * @throws RuntimeException if amount of available connections less then parameter poolSize
     */
    private void checkAvailableConnections() throws RuntimeException {
        if (availableConnections.size() < dbConfiguration.getPoolSize()) {
            log.fatal("Can't initialize connection pool.");
            throw new RuntimeException("Can't initialize connection pool.");
        }
    }

    /**
     * Gives a connection to the database
     * @return Connection
     */
    public Connection takeConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            log.trace("Take connection");
        } catch (InterruptedException e) {
            log.error("Can't return connection.", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     *
     * @param connection
     * @return Connection
     */
    public boolean releaseConnection(Connection connection) {
        if (connection.getClass().equals(ProxyConnection.class)) {
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            availableConnections.offer(proxyConnection);
            log.trace("Connections are released");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Destroys the connection pool
     * @throws SQLException
     * @throws InterruptedException
     */
    public void destroyConnectionPool() throws SQLException, InterruptedException {
        if (isCreate.get()) {
            lock.lock();
            try {
                for (int i = 0; i < dbConfiguration.getPoolSize(); i++) {
                    ProxyConnection connection = availableConnections.take();
                    connection.closeConnection();
                }
                isCreate.set(false);
                instance = null;
            } finally {
                lock.unlock();
            }
        }
        log.debug("Connection pool is destroyed");
    }

    /**
     * Deregistered drivers
     * @throws SQLException if can't deregister drivers
     */
    public void deregisterDriver() throws SQLException {
        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
        while (driverEnumeration.hasMoreElements()) {
            DriverManager.deregisterDriver(driverEnumeration.nextElement());
        }
        log.info("Driver deregistered");
    }

}
