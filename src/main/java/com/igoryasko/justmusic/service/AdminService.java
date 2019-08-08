package com.igoryasko.justmusic.service;

import com.igoryasko.justmusic.dao.GenreDAO;
import com.igoryasko.justmusic.dao.SingerDao;
import com.igoryasko.justmusic.dao.TransactionHelper;
import com.igoryasko.justmusic.entity.Genre;
import com.igoryasko.justmusic.entity.Singer;
import com.igoryasko.justmusic.exception.DaoException;
import com.igoryasko.justmusic.exception.ServiceException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AdminService {

    private TransactionHelper helper;
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
        }finally {
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
        }finally {
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
        }finally {
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
        }finally {
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
        }finally {
            helper.end();
        }
    }

}
