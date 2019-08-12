package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.entity.Genre;
import com.igoryasko.justmusic.exception.DaoException;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Log4j2
public class GenreDAO extends AbstractDAO<Genre> {

    private static GenreDAO instance;

    public static GenreDAO getInstance() {
        if (instance == null) {
            instance = new GenreDAO();
        }
        return instance;
    }

    private GenreDAO() {

    }

    @Language("SQL")
    private static final String SELECT_ALL_GENERS = "SELECT genre_id, genre_name FROM genres";
    @Language("SQL")
    private static final String FIND_GENER_BY_ID = "SELECT * FROM genres WHERE genre_id=?";
    @Language("SQL")
    private static final String DELETE_GENER_BY_ID = "DELETE FROM genres WHERE genre_id=?";
    @Language("SQL")
    private static final String INSERT_GENRE = "INSERT INTO genres (genre_name) VALUES(?)";
    @Language("SQL")
    private static final String FIND_GENRE_BY_NAME =
            "SELECT genre_id, genre_name FROM genres WHERE genre_name=?";

    @Override
    public List<Genre> findAll() throws DaoException {
        return null;
    }

    @Override
    public Genre findById(long id) throws DaoException {
        Genre genre = new Genre();
        try (PreparedStatement pr = connection.prepareStatement(FIND_GENER_BY_ID)) {
            pr.setLong(1, id);
            try (ResultSet resultSet = pr.executeQuery()) {
                resultSet.next();
                genre.setGenreId(resultSet.getLong(1));
                genre.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            log.debug("Find genre: " + genre);
            throw new DaoException(e);
        }
        return genre;
    }

    @Override
    public boolean create(Genre genre) throws DaoException {
        try (PreparedStatement pr = connection.prepareStatement(INSERT_GENRE)) {
            pr.setString(1, genre.getName());
            int result = pr.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            log.info("Create genre: " + genre);
            throw new DaoException(e);
        }
        return false;
    }

    public long createAndGetId(Genre genre) throws DaoException {
        long res = 0;
        try (PreparedStatement pr = connection.prepareStatement(INSERT_GENRE, Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, genre.getName());
            pr.executeUpdate();
            // TODO: 11.08.2019 close rs
            ResultSet resultSet = pr.getGeneratedKeys();
            if (resultSet.next()) {
                res = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            log.info("Create genre: " + genre);
            throw new DaoException(e);
        }
        return res;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Genre entity) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Genre entity) throws DaoException {
        return false;
    }

    public Genre findByName(String name) throws DaoException {
        Genre genre = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_GENRE_BY_NAME)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    genre = new Genre();
                    genre.setGenreId(resultSet.getLong(1));
                    genre.setName(resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            log.debug("Find genre by name: " + name);
            throw new DaoException(e);
        }
        return genre;
    }

}
