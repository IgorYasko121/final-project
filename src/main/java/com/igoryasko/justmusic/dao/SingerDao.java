package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.entity.Singer;
import com.igoryasko.justmusic.exception.DaoException;
import org.intellij.lang.annotations.Language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SingerDao extends AbstractDAO<Singer> {

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
    public List<Singer> findAll() throws DaoException {
        return null;
    }

    @Override
    public Singer findById(long id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Singer entity) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Singer singer) throws DaoException {
        try (PreparedStatement pr = connection.prepareStatement(INSERT_SINGER)) {
            pr.setString(1, singer.getName());
            int result = pr.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
        return false;
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
            e.printStackTrace();
            throw new DaoException(e);
        }
        return res;
    }

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
            throw new DaoException(e);
        }
        return singer;
    }

}
