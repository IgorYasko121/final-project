package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.connectionpool.ProxyConnection;
import com.igoryasko.justmusic.entity.Entity;
import com.igoryasko.justmusic.exception.DaoException;

import java.sql.Connection;
import java.util.List;


public abstract class AbstractDAO<T extends Entity> {

    protected Connection connection;

    public abstract List<T> findAll() throws DaoException;

    public abstract T findById(long id) throws DaoException;

    public abstract boolean delete(long id) throws DaoException;

    public abstract boolean delete(T entity) throws DaoException;

    public abstract boolean create(T entity) throws DaoException;

    public abstract boolean update(T entity) throws DaoException;

    void setConnection(ProxyConnection connection) {
        this.connection = connection;
    }

}
