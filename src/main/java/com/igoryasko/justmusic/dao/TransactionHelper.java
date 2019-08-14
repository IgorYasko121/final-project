package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.connectionpool.ConnectionPool;
import com.igoryasko.justmusic.connectionpool.ProxyConnection;
import lombok.extern.log4j.Log4j2;

import java.sql.SQLException;

/**
 * The {@code Transaction Helper} class provides methods for setting up a Dao connection
 * and also provides an implementation of transactions.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class TransactionHelper {

    private ProxyConnection connection;
    private ConnectionPool connectionPool;

    public TransactionHelper() {
        init();
    }

    private void init() {
        connectionPool = ConnectionPool.getInstance();
        connection = (ProxyConnection) connectionPool.takeConnection();
        log.debug("Initialize TransactionHelper");
    }

    public void begin(AbstractDAO dao) {
        dao.setConnection(connection);
    }

    public void end() {
        connectionPool.releaseConnection(connection);
    }

    public void beginTransaction(AbstractDAO dao, AbstractDAO ... daos) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            log.error("Couldn't set autocommit", e);
        }
        dao.setConnection(connection);
        for (AbstractDAO d : daos){
            d.setConnection(connection);
        }
    }

    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("Couldn't set autocommit.", e);
        }
        connectionPool.releaseConnection(connection);
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            log.error("Couldn't set commit.", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            log.error("Couldn't set rollback.", e);
        }
    }

}
