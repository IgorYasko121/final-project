package com.igoryasko.justmusic.service;

import com.igoryasko.justmusic.dao.TrackDAO;
import com.igoryasko.justmusic.dao.TransactionHelper;
import com.igoryasko.justmusic.entity.Track;
import com.igoryasko.justmusic.exception.DaoException;
import com.igoryasko.justmusic.exception.ServiceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * The class {@code AdminService} provides functional for track.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class TrackService {

    private TransactionHelper helper;
    private TrackDAO trackDAO = TrackDAO.getInstance();

    /**
     * Check exist track name
     * @return if user exists return User.Role
     */
    public boolean checkTrack(String fileName) throws ServiceException {
        helper = new TransactionHelper();
        helper.begin(trackDAO);
        try {
            log.info("Check track name: " + fileName);
            return trackDAO.checkFileName(fileName);
        } catch (DaoException e) {
            log.error("DaoException :" + e);
            throw new ServiceException("Service execute fail" + e);
        }finally {
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
            log.error("DaoException :" + e);
            throw new ServiceException("Service execute fail" + e);
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
            log.error("DaoException :" + e);
            throw new ServiceException("Service execute fail" + e);
        }finally {
            helper.end();
        }
    }

    public List<Track> findTopFiveTracks() throws ServiceException {
        helper = new TransactionHelper();
        List<Track> tracks;
        helper.begin(trackDAO);
        try {
            tracks = trackDAO.findTopFive();
            log.debug("Find top five tracks");
            return tracks;
        } catch (DaoException e) {
            log.error("DaoException :" + e);
            throw new ServiceException("Service execute fail" + e);
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
            log.error("DaoException :" + e);
            throw new ServiceException("Service execute fail" + e);
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
            log.error("DaoException :" + e);
            throw new ServiceException("Service execute fail" + e);
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
            log.error("DaoException :" + e);
            throw new ServiceException("Service execute fail" + e);
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
            log.error("DaoException :" + e);
            throw new ServiceException("Service execute fail" + e);
        } finally {
            helper.end();
        }
    }

}
