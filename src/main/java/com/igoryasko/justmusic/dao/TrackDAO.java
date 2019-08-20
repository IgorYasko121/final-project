package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.entity.Genre;
import com.igoryasko.justmusic.entity.Singer;
import com.igoryasko.justmusic.entity.Track;
import com.igoryasko.justmusic.exception.DaoException;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code TrackDAO} class provides an implementation of methods for working with database table tracks.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class TrackDAO extends AbstractDAO implements DAO<Track> {

    private static TrackDAO instance;

    public static TrackDAO getInstance() {
        if (instance == null) {
            instance = new TrackDAO();
        }
        return instance;
    }

    private TrackDAO() {

    }

    @Language("SQL")
    private static final String FIND_ALL_TRACKS =
            "SELECT track_id, track_name, track_path, g.genre_name, s.signer_name FROM tracks t\n" +
                    "INNER JOIN genres g ON t.genre_id = g.genre_id INNER JOIN signers s on t.signer_id = s.signer_id";
    @Language("SQL")
    private static final String SELECT_ALL_TRACKS_BY_LIMIT = "SELECT track_id, track_name, track_path, g.genre_name, s.signer_name " +
            "FROM tracks tr INNER JOIN genres g on tr.genre_id = g.genre_id INNER JOIN signers s on tr.signer_id = s.signer_id " +
            "ORDER BY track_id LIMIT ? OFFSET ?";
    @Language("SQL")
    private static final String SELECT_FIVE_TRACKS = "SELECT track_id, track_name, track_path, g.genre_name, s.signer_name FROM tracks t" +
            " INNER JOIN genres g on t.genre_id = g.genre_id INNER JOIN signers s ON t.signer_id = s.signer_id LIMIT 5";
    @Language("SQL")
    private static final String FIND_TRACK_BY_ID = "SELECT track_id, track_name FROM tracks WHERE track_id=?";
    @Language("SQL")
    private static final String FIND_TRACK_BY_PATH = "SELECT track_id, track_name FROM tracks WHERE track_path=?";
    @Language("SQL")
    private static final String FIND_FAVORITES_TRACK = "SELECT tr.track_id ,tr.track_path, tr.track_name, g.genre_name, s.signer_name FROM tracks tr INNER JOIN users_tracks ut ON\n" +
            "            tr.track_id = ut.track_id INNER JOIN users us ON ut.user_id = us.user_id INNER JOIN genres g ON tr.genre_id = g.genre_id\n" +
            "INNER JOIN signers s ON tr.signer_id = s.signer_id where us.user_id = ?;";
    @Language("SQL")
    private static final String DELETE_TRACK_BY_ID = "DELETE FROM tracks WHERE track_id=?";
    @Language("SQL")
    private static final String DELETE_FAVORITE = "DELETE FROM users_tracks WHERE track_id=? AND user_id=?";
    @Language("SQL")
    private static final String INSERT_FAVORITE = "INSERT INTO users_tracks (user_id, track_id) VALUES (?,?)";
    @Language("SQL")
    private static final String FIND_FAVORITE = "SELECT FROM users_tracks WHERE user_id=? AND track_id=?";
    @Language("SQL")
    private static final String INSERT_TRACK =
            "INSERT INTO tracks (track_name, track_path, genre_id, signer_id) VALUES(?,?,?,?)";
    @Language("SQL")
    private static final String FIND_COUNT_BY_ID = "SELECT COUNT(track_id) FROM tracks";
    @Language("SQL")
    private static final String UPDATE_TRACK =
            "UPDATE tracks SET track_name=?, genre_id=?, signer_id=? WHERE track_id=?";

    @Override
    public boolean create(Track track) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRACK)) {
            preparedStatement.setString(1, track.getName());
            preparedStatement.setString(2, track.getPath());
            preparedStatement.setLong(3, track.getGenre().getGenreId());
            preparedStatement.setLong(4, track.getSinger().getSingerId());
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return false;
    }

    @Override
    public boolean update(Track track) throws DaoException {
        try (PreparedStatement pr = connection.prepareStatement(UPDATE_TRACK)) {
            pr.setString(1, track.getName());
            pr.setLong(2, track.getGenre().getGenreId());
            pr.setLong(3, track.getSinger().getSingerId());
            pr.setLong(4, track.getTrackId());
            int result = pr.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return false;
    }

    @Override
    public boolean delete(Track entity) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TRACK_BY_ID)) {
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return false;
    }

    @Override
    public List<Track> findAll() throws DaoException {
        List<Track> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TRACKS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Track track = new Track();
                    Genre genre = new Genre();
                    Singer singer = new Singer();
                    track.setTrackId(resultSet.getLong(1));
                    track.setName(resultSet.getString(2));
                    track.setPath(resultSet.getString(3));
                    genre.setName(resultSet.getString(4));
                    singer.setName(resultSet.getString(5));
                    track.setGenre(genre);
                    list.add(track);
                }
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return list;
    }

    @Override
    public Track findById(long id) throws DaoException {
        Track track = new Track();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_TRACK_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                track.setTrackId(resultSet.getLong(1));
                track.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException(e);
        }
        return track;
    }

    public boolean checkFileName(String fileName) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_TRACK_BY_PATH)) {
            preparedStatement.setString(1, fileName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return false;
    }

    public List<Track> findTopFive() throws DaoException {
        List<Track> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FIVE_TRACKS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Track track = new Track();
                    track.setTrackId(resultSet.getLong(1));
                    track.setName(resultSet.getString(2));
                    track.setPath(resultSet.getString(3));
                    track.setGenre(new Genre(resultSet.getString(4)));
                    track.setSinger(new Singer(resultSet.getString(5)));
                    list.add(track);
                }
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return list;
    }

    public int findCountById() throws DaoException {
        int result;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_COUNT_BY_ID)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return result;
    }

    public List<Track> findByLimit(int limit, int offSet) throws DaoException {
        List<Track> list = new ArrayList<>();
        try (PreparedStatement pr = connection.prepareStatement(SELECT_ALL_TRACKS_BY_LIMIT)) {
            pr.setLong(1, limit);
            pr.setLong(2, offSet);
            try (ResultSet resultSet = pr.executeQuery()) {
                while (resultSet.next()) {
                    Track track = new Track();
                    track.setTrackId(resultSet.getLong(1));
                    track.setName(resultSet.getString(2));
                    track.setPath(resultSet.getString(3));
                    Genre genre = new Genre();
                    genre.setName(resultSet.getString(4));
                    track.setGenre(genre);
                    Singer singer = new Singer();
                    singer.setName(resultSet.getString(5));
                    track.setSinger(singer);
                    list.add(track);
                }
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return list;
    }

    public boolean addFavorite(long userId, long trackId) throws DaoException {
        try (PreparedStatement pr = connection.prepareStatement(INSERT_FAVORITE)) {
            pr.setLong(1, userId);
            pr.setLong(2, trackId);
            int result = pr.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DaoException("Dao statement fail" + e);
        }
        return false;
    }

    public boolean checkFavorite(long userId, long trackId) throws DaoException {
        try (PreparedStatement pr = connection.prepareStatement(FIND_FAVORITE)) {
            pr.setLong(1, userId);
            pr.setLong(2, trackId);
            try (ResultSet resultSet = pr.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return false;
    }

    public List<Track> findFavoritesTracks(long userId) throws DaoException {
        List<Track> list = new ArrayList<>();
        try (PreparedStatement pr = connection.prepareStatement(FIND_FAVORITES_TRACK)) {
            pr.setLong(1, userId);
            try (ResultSet resultSet = pr.executeQuery()) {
                while (resultSet.next()) {
                    Track track = new Track();
                    Genre genre = new Genre();
                    Singer singer = new Singer();
                    track.setTrackId(resultSet.getLong(1));
                    track.setPath(resultSet.getString(2));
                    track.setName(resultSet.getString(3));
                    genre.setName(resultSet.getString(4));
                    singer.setName(resultSet.getString(5));
                    track.setGenre(genre);
                    track.setSinger(singer);
                    list.add(track);
                }
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return list;
    }

    public boolean deleteFavorite(long trackId, long userId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FAVORITE)) {
            preparedStatement.setLong(1, trackId);
            preparedStatement.setLong(2, userId);
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return false;
    }


}
