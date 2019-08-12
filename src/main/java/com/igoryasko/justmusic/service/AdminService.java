package com.igoryasko.justmusic.service;

import com.igoryasko.justmusic.dao.GenreDAO;
import com.igoryasko.justmusic.dao.SingerDao;
import com.igoryasko.justmusic.dao.TrackDAO;
import com.igoryasko.justmusic.dao.TransactionHelper;
import com.igoryasko.justmusic.entity.Genre;
import com.igoryasko.justmusic.entity.Singer;
import com.igoryasko.justmusic.entity.Track;
import com.igoryasko.justmusic.exception.DaoException;
import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.validator.TrackValidator;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AdminService {

    private TransactionHelper helper;
    private TrackDAO trackDAO = TrackDAO.getInstance();
    private GenreDAO genreDAO = GenreDAO.getInstance();
    private SingerDao singerDao = SingerDao.getInstance();

    public long addSinger(String name) throws ServiceException {
        helper = new TransactionHelper();
        Singer singer = new Singer(name);
        helper.begin(singerDao);
        try {
            log.debug("Add singer");
            return singerDao.createAndGetId(singer);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.end();
        }
    }

    public long addGenre(String tittle) throws ServiceException {
        helper = new TransactionHelper();
        Genre genre = new Genre(tittle);
        helper.begin(genreDAO);
        try {
            log.debug("Add genre " + genre);
            return genreDAO.createAndGetId(genre);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.end();
        }
    }

    public Genre findGenreById(long genreId) throws ServiceException {
        helper = new TransactionHelper();
        helper.begin(genreDAO);
        try {
            log.debug("Get genre by id");
            return genreDAO.findById(genreId);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.end();
        }
    }

    public Singer findSingerByName(String name) throws ServiceException {
        helper = new TransactionHelper();
        helper.begin(singerDao);
        try {
            log.debug("Find singer by name");
            return singerDao.findByName(name);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.end();
        }
    }

    public Genre findGenreByName(String name) throws ServiceException {
        helper = new TransactionHelper();
        helper.begin(genreDAO);
        try {
            log.debug("Find genre by name");
            return genreDAO.findByName(name);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.end();
        }
    }

    public boolean updateTrack(Track track) throws ServiceException {
        helper = new TransactionHelper();
        helper.beginTransaction(trackDAO, singerDao, genreDAO);
        Singer singer;
        Genre genre;
        Track newTrack;
        try {
            // TODO: 11.08.2019 copyPast
            singer = singerDao.findByName(track.getSinger().getName());
            if (singer == null) {
                singer = new Singer(track.getSinger().getName());
                long singerId = singerDao.createAndGetId(singer);
                singer.setSingerId(singerId);
                track.setSinger(singer);
            }
            genre = genreDAO.findByName(track.getGenre().getName());
            if (genre == null) {
                genre = new Genre(track.getGenre().getName());
                long genreId = genreDAO.createAndGetId(genre);
                genre.setGenreId(genreId);
                track.setGenre(genre);
            }
            newTrack = new Track(track.getTrackId(), track.getName(), genre, singer);
            trackDAO.update(newTrack);
            helper.commit();
            log.info("Update track: " + newTrack);
            return true;
        } catch (DaoException e) {
            helper.rollback();
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.endTransaction();
        }
    }

    public boolean addTrack(String trackName, String fileNameDb, String genreName, String singerName) throws ServiceException {
        helper = new TransactionHelper();
        helper.beginTransaction(trackDAO, singerDao, genreDAO);
        Track track;
        Singer singer;
        Genre genre;
        try {
            singer = singerDao.findByName(singerName);
            if (singer == null) {
                singer = new Singer(singerName);
                long singerId = singerDao.createAndGetId(singer);
                singer.setSingerId(singerId);
            }
            genre = genreDAO.findByName(genreName);
            if (genre == null) {
                genre = new Genre(genreName);
                long genreId = genreDAO.createAndGetId(genre);
                genre.setGenreId(genreId);
            }
            track = new Track(trackName, fileNameDb, genre, singer);
            trackDAO.create(track);
            helper.commit();
            return true;
        } catch (DaoException e) {
            helper.rollback();
            log.error(e);
            throw new ServiceException(e);
        } finally {
            helper.endTransaction();
        }
    }

}
