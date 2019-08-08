package com.igoryasko.justmusic.service;

import com.igoryasko.justmusic.dao.TransactionHelper;
import com.igoryasko.justmusic.dao.UserDAO;
import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.exception.DaoException;
import com.igoryasko.justmusic.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@Log4j2
public class UserService {

    private TransactionHelper helper;
    private UserDAO userDAO = UserDAO.getInstance();

    /**
     * Checks login and password
     * @return if user exists return User.Role
     */
    public User.Role checkUser(String login, String password) throws ServiceException {
        helper = new TransactionHelper();
        helper.begin(userDAO);
        try {
            log.info("Check user");
            return userDAO.findByLoginPassword(login, password);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }finally {
            helper.end();
        }
    }

    public boolean registerUser(String firstName, String lastName, String login, String password, String email) throws ServiceException {
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        helper = new TransactionHelper();
        User user = new User(firstName, lastName, login, hash, email);
        log.debug(user);
        helper.begin(userDAO);
        try {
            userDAO.create(user);
            log.info("Register user");
            return true;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }finally {
            helper.end();
        }
    }

    public boolean updateUser(String firstName, String lastName, String login, String password, String email) throws ServiceException {
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        helper = new TransactionHelper();
        User user = new User(firstName, lastName, login, hash, email);
        log.debug(user);
        helper.begin(userDAO);
        try {
            userDAO.update(user);
            log.info("Update user");
            return true;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }finally {
            helper.end();
        }
    }

    public List<User> findAllUsers() throws ServiceException {
        helper = new TransactionHelper();
        List<User> users;
        helper.begin(userDAO);
        try {
            users = userDAO.findAll();
            log.info("Find all users");
            return users;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }finally {
            helper.end();
        }
    }

    public List<User> findLimitUsers(int recordsPerPage ,int currentPage) throws ServiceException {
        helper = new TransactionHelper();
        int startPosition = currentPage * recordsPerPage - recordsPerPage;
        List<User> users;
        helper.begin(userDAO);
        try {
            users = userDAO.findByLimit(recordsPerPage ,startPosition);
            log.info("Find all users by limit");
            return users;
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
        helper.begin(userDAO);
        try {
            numOfRows = userDAO.findCountById();
            log.info("Find all users rows");
            return numOfRows;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }finally {
            helper.end();
        }
    }

    public User findUserById(long userId) throws ServiceException {
        helper = new TransactionHelper();
        User users;
        helper.begin(userDAO);
        try {
            users = userDAO.findById(userId);
            log.info("Get all users");
            return users;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }finally {
            helper.end();
        }
    }

    public boolean deleteUser(long userId) throws ServiceException {
        helper = new TransactionHelper();
        helper.begin(userDAO);
        try {
            log.info("Delete user");
            return userDAO.delete(userId);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }finally {
            helper.end();
        }
    }

    public boolean updateRoleUser(String role, long id) throws ServiceException {
        helper = new TransactionHelper();
        helper.begin(userDAO);
        try {
            userDAO.updateRoleById(role, id);
            log.info("Update user");
            return true;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }finally {
            helper.end();
        }
    }

    public boolean checkLogin(String login) throws ServiceException {
        helper = new TransactionHelper();
        helper.begin(userDAO);
        try {
            log.info("Check user");
            return userDAO.findByLogin(login);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }finally {
            helper.end();
        }
    }

    public User findUserByName(String name) throws ServiceException {
        helper = new TransactionHelper();
        helper.begin(userDAO);
        try {
            log.debug("Find user by name");
            return userDAO.findByName(name);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }finally {
            helper.end();
        }
    }

}
