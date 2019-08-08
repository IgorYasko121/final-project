package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.entity.Genre;
import com.igoryasko.justmusic.entity.Singer;
import com.igoryasko.justmusic.entity.Track;
import com.igoryasko.justmusic.exception.DaoException;
import org.intellij.lang.annotations.Language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO extends AbstractDAO<Track> {

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
            "SELECT track_id, track_name, track_path, gn.genre_id ,genre_name FROM tracks as tr\n" +
                    "INNER JOIN genres gn ON tr.genre_id = gn.genre_id";
    @Language("SQL")
    private static final String SELECT_ALL_TRACKS_BY_LIMIT = "SELECT track_id, track_name, track_path, g.genre_name, s.signer_name " +
            "FROM tracks tr INNER JOIN genres g on tr.genre_id = g.genre_id INNER JOIN signers s on tr.signer_id = s.signer_id " +
            "ORDER BY track_id LIMIT ? OFFSET ?";
    @Language("SQL")
    private static final String SELECT_TOP_SIX_TRACKS = "SELECT track_name, track_path, g.genre_name, s.signer_name FROM tracks t" +
            " INNER JOIN genres g on t.genre_id = g.genre_id INNER JOIN signers s ON t.signer_id = s.signer_id LIMIT 6";
    @Language("SQL")
    private static final String FIND_TRACK_BY_ID = "SELECT * FROM tracks WHERE track_id=?";
    @Language("SQL")
    private static final String FIND_FAVORITES_TRACK = "SELECT tr.track_path, tr.track_name, g.genre_name, s.signer_name FROM tracks tr INNER JOIN users_tracks ut ON\n" +
            "            tr.track_id = ut.track_id INNER JOIN users us ON ut.user_id = us.user_id INNER JOIN genres g ON tr.genre_id = g.genre_id\n" +
            "INNER JOIN signers s ON tr.signer_id = s.signer_id where us.user_id = ?;";
    @Language("SQL")
    private static final String DELETE_TRACK_BY_ID = "DELETE FROM tracks WHERE track_id=?";
    @Language("SQL")
    private static final String INSERT_TRACK =
            "INSERT INTO tracks (track_name, track_path, genre_id, signer_id) VALUES(?,?,?,?)";
    @Language("SQL")
    private static final String INSERT_USERS_TRACKS = "INSERT INTO users_tracks (user_id, track_id) VALUES (?,?)";
    @Language("SQL")
    private static final String FIND_COUNT_BY_ID =
            "SELECT COUNT(track_id) FROM tracks";

    @Override
    public List<Track> findAll() throws DaoException {
        List<Track> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TRACKS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Track track = new Track();
                    Genre genre = new Genre();
                    track.setTrackId(resultSet.getLong(1));
                    track.setName(resultSet.getString(2));
                    track.setPath(resultSet.getString(3));
                    genre.setGenreId(resultSet.getLong(4));
                    genre.setName(resultSet.getString(5));
                    track.setGenre(genre);
                    list.add(track);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
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
            throw new DaoException(e);
        }
        return track;
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
            throw new DaoException(e);
        }
        return false;
    }

    @Override
    public boolean delete(Track entity) {
        return false;
    }

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
            throw new DaoException(e);
        }
        return false;
    }

    @Override
    public boolean update(Track entity) {
        return false;
    }

    public List<Track> findTopSix() throws DaoException {
        List<Track> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TOP_SIX_TRACKS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Track track = new Track();
                    track.setName(resultSet.getString(1));
                    track.setPath(resultSet.getString(2));
                    track.setGenre(new Genre(resultSet.getString(3)));
                    track.setSinger(new Singer(resultSet.getString(4)));
                    list.add(track);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
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
            throw new DaoException(e);
        }
        return result;
    }

    public List<Track> findByLimit(int limit ,int offSet) throws DaoException {
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
            throw new DaoException(e);
        }
        return list;
    }

    public boolean createUserTrack(long userId, long trackId) throws DaoException {
        try (PreparedStatement pr = connection.prepareStatement(INSERT_USERS_TRACKS)) {
            pr.setLong(1, userId);
            pr.setLong(2, trackId);
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

    public List<Track> findFavoritesTracks(long userId) throws DaoException {
        List<Track> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_FAVORITES_TRACK)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Track track = new Track();
                    Genre genre = new Genre();
                    Singer singer = new Singer();
                    track.setPath(resultSet.getString(1));
                    track.setName(resultSet.getString(2));
                    genre.setName(resultSet.getString(3));
                    singer.setName(resultSet.getString(4));
                    track.setGenre(genre);
                    list.add(track);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return list;
    }

}
