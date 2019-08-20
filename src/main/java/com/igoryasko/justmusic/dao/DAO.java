package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.entity.Entity;
import com.igoryasko.justmusic.exception.DaoException;

import java.util.List;

public interface DAO<T extends Entity> {

    boolean create(T entity) throws DaoException;

    boolean update(T entity) throws DaoException;

    boolean delete(T entity) throws DaoException;

    List<T> findAll() throws DaoException;

    T findById(long id) throws DaoException;

    boolean delete(long id) throws DaoException;

}
