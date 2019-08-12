package com.igoryasko.justmusic.service;

import com.igoryasko.justmusic.dao.TrackDAO;
import com.igoryasko.justmusic.dao.TransactionHelper;
import com.igoryasko.justmusic.entity.Genre;
import com.igoryasko.justmusic.entity.Singer;
import com.igoryasko.justmusic.entity.Track;
import com.igoryasko.justmusic.exception.DaoException;
import com.igoryasko.justmusic.exception.ServiceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class TrackService {

    private TransactionHelper helper;
    private TrackDAO trackDAO = TrackDAO.getInstance();

    public boolean addTrack(String trackName, String fileNameDb, Genre genre, Singer singer) throws ServiceException {
        helper = new TransactionHelper();
        Track track = new Track(trackName, fileNameDb, genre, singer);
        helper.begin(trackDAO);
        try {
            trackDAO.create(track);
            log.debug("Add track");
            return true;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.end();
        }
    }

    public List<Track> findAllTracks() throws ServiceException {
        helper = new TransactionHelper();
        List<Track> tracks;
        helper.begin(trackDAO);
        try {
            tracks = trackDAO.findAll();
            log.debug("Find all tracks");
            return tracks;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.end();
        }
    }

    public List<Track> findLimitTracks(int recordsPerPage ,int currentPage) throws ServiceException {
        helper = new TransactionHelper();
        int startPosition = currentPage * recordsPerPage - recordsPerPage;
        List<Track> tracks;
        helper.begin(trackDAO);
        try {
            tracks = trackDAO.findByLimit(recordsPerPage ,startPosition);
            log.debug("Find all users by limit");
            return tracks;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }finally {
            helper.end();
        }
    }

    public int getNumberOfRows() throws ServiceException {
        int numOfRows;
        helper = new TransactionHelper();
        helper.begin(trackDAO);
        try {
            numOfRows = trackDAO.findCountById();
            log.debug("Find all tracks rows");
            return numOfRows;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }finally {
            helper.end();
        }
    }

    public List<Track> findTopSixTracks() throws ServiceException {
        helper = new TransactionHelper();
        List<Track> tracks;
        helper.begin(trackDAO);
        try {
            tracks = trackDAO.findTopFive();
            log.debug("Find top ten tracks");
            return tracks;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.end();
        }
    }

    public List<Track> findFavoriteTracks(long userId) throws ServiceException {
        helper = new TransactionHelper();
        List<Track> tracks;
        helper.begin(trackDAO);
        try {
            tracks = trackDAO.findFavoritesTracks(userId);
            log.debug("Find favorites tracks");
            return tracks;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.end();
        }
    }

    public boolean deleteTrack(long trackId) throws ServiceException {
        helper = new TransactionHelper();
        helper.begin(trackDAO);
        boolean res;
        try {
            res = trackDAO.delete(trackId);
            log.info("Delete track: " + trackId);
            return res;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.end();
        }
    }

    public boolean deleteFromFavorite(long trackId, long userId) throws ServiceException{
        helper = new TransactionHelper();
        helper.begin(trackDAO);
        boolean res;
        try {
            res = trackDAO.deleteFavorite(trackId, userId);
            log.debug("Delete trackId " + trackId + "userId " + userId + "from favorite");
            return res;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.end();
        }
    }

    public boolean addToFavorite(long userId, long trackId) throws ServiceException {
        helper = new TransactionHelper();
        helper.begin(trackDAO);
        try {
            log.info("Add track to users_tracks table " + userId + " " + trackId);
            return !trackDAO.checkFavorite(userId, trackId) && trackDAO.addFavorite(userId, trackId);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.end();
        }
    }

}
