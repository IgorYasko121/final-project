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

/**
 * The class {@code AdminService} provides functional for admin role.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class AdminService {

    private TransactionHelper helper;
    private TrackDAO trackDAO = TrackDAO.getInstance();
    private GenreDAO genreDAO = GenreDAO.getInstance();
    private SingerDao singerDao = SingerDao.getInstance();

    public boolean updateTrack(Track track) throws ServiceException {
        helper = new TransactionHelper();
        helper.beginTransaction(trackDAO, singerDao, genreDAO);
        Singer singer;
        Genre genre;
        Track newTrack;
        try {
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
