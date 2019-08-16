package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.entity.Entity;
import com.igoryasko.justmusic.exception.DaoException;

public interface DAO<T extends Entity> {

    boolean create(T entity) throws DaoException;

    boolean update(T entity) throws DaoException;

    boolean delete(T entity) throws DaoException;

}
