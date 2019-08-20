package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.entity.Singer;
import com.igoryasko.justmusic.exception.DaoException;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code SingerDao} class provides an implementation of methods for working with database table singers.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class SingerDao extends AbstractDAO implements DAO<Singer> {

    private static SingerDao instance;

    public static SingerDao getInstance() {
        if (instance == null) {
            instance = new SingerDao();
        }
        return instance;
    }

    private SingerDao() {

    }

    @Language("SQL")
    private static final String INSERT_SINGER = "INSERT INTO signers (signer_name) VALUES(?)";

    @Language("SQL")
    private static final String FIND_SINGER_BY_NAME =
            "SELECT signer_id, signer_name FROM signers WHERE signer_name=?";

    @Override
    public boolean create(Singer singer) throws DaoException {
        try (PreparedStatement pr = connection.prepareStatement(INSERT_SINGER)) {
            pr.setString(1, singer.getName());
            int result = pr.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail", e);
        }
        return false;
    }

    /**
     * The method doesn't supported
     */
    @Override
    public boolean delete(Singer entity) throws DaoException {
        return false;
    }

    /**
     * The method doesn't supported
     */
    @Override
    public boolean update(Singer entity) throws DaoException {
        return false;
    }

    public Singer findByName(String name) throws DaoException {
        Singer singer = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_SINGER_BY_NAME)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    singer = new Singer();
                    singer.setSingerId(resultSet.getLong(1));
                    singer.setName(resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail", e);
        }
        return singer;
    }

    public long createAndGetId(Singer singer) throws DaoException {
        long res = 0;
        try (PreparedStatement pr = connection.prepareStatement(INSERT_SINGER, Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, singer.getName());
            pr.executeUpdate();
            ResultSet resultSet = pr.getGeneratedKeys();
            if (resultSet.next()) {
                res = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail", e);
        }
        return res;
    }


    /**
     * The method doesn't supported
     */
    @Override
    public List<Singer> findAll() throws DaoException {
        return new ArrayList<>();
    }

    /**
     * The method doesn't supported
     */
    @Override
    public Singer findById(long id) throws DaoException {
        return new Singer();
    }

    /**
     * The method doesn't supported
     */
    @Override
    public boolean delete(long id) throws DaoException {
        return false;
    }

}
