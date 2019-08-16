package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.exception.DaoException;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code UserDAO} class provides an implementation of methods for working with database table users.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class UserDAO extends AbstractDAO<User> implements DAO<User> {

    private static UserDAO instance;

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    private UserDAO() {

    }

    @Language("SQL")
    private static final String SELECT_ALL_USERS = "SELECT user_id, first_name, last_name, login, role, e_mail, created_at " +
            "FROM users ORDER BY user_id";
    @Language("SQL")
    private static final String SELECT_ALL_USERS_BY_LIMIT = "SELECT user_id, first_name, last_name, login, role, e_mail, created_at " +
            "FROM users ORDER BY user_id LIMIT ? OFFSET ?";
    @Language("SQL")
    private static final String FIND_USER_BY_ID = "SELECT user_id, first_name, last_name FROM users WHERE user_id=?";
    @Language("SQL")
    private static final String FIND_USER_BY_LOGIN_PASSWORD = "SELECT password, role FROM users WHERE login=?";
    @Language("SQL")
    private static final String FIND_USER_BY_LOGIN = "SELECT user_id, login FROM users WHERE login=?";
    @Language("SQL")
    private static final String FIND_COUNT_BY_ID = "SELECT COUNT(user_id) FROM users";
    @Language("SQL")
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id=?";
    @Language("SQL")
    private static final String DELETE_USER =
            "DELETE FROM users WHERE user_id=? AND first_name=? AND last_name=?";
    @Language("SQL")
    private static final String INSERT_USER =
            "INSERT INTO users (first_name, last_name, login, password, role, e_mail) VALUES(?,?,?,?,?,?)";
    @Language("SQL")
    private static final String UPDATE_USER =
            "UPDATE users SET first_name=?, last_name=?, password=?, e_mail=? WHERE login=?";
    @Language("SQL")
    private static final String UPDATE_USER_ROLE =
            "UPDATE users SET role=? WHERE user_id=?";

    @Override
    public boolean create(User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, String.valueOf(user.getRole()));
            preparedStatement.setString(6, user.getEmail());
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
    public boolean update(User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getLogin());
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
    public boolean delete(User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, user.getUserId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
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
    public List<User> findAll() throws DaoException {
        List<User> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setUserId(resultSet.getLong(1));
                    user.setFirstName(resultSet.getString(2));
                    user.setLastName(resultSet.getString(3));
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return list;
    }

    public List<User> findByLimit(int limit ,int offSet) throws DaoException {
        List<User> list = new ArrayList<>();
        try (PreparedStatement pr = connection.prepareStatement(SELECT_ALL_USERS_BY_LIMIT)) {
            pr.setLong(1, limit);
            pr.setLong(2, offSet);
            try (ResultSet resultSet = pr.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setUserId(resultSet.getLong(1));
                    user.setFirstName(resultSet.getString(2));
                    user.setLastName(resultSet.getString(3));
                    user.setLogin(resultSet.getString(4));
                    user.setRole(User.Role.valueOf(resultSet.getString(5)));
                    user.setEmail(resultSet.getString(6));
                    user.setCreatedAt(resultSet.getDate(7).toLocalDate());
                    list.add(user);
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

    public User findById(long id) throws DaoException {
        User user = new User();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                user.setUserId(resultSet.getLong(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return user;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID)) {
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



    public boolean updateRoleById(String role, long id) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_ROLE)) {
            preparedStatement.setString(1, String.valueOf(role));
            preparedStatement.setLong(2, id);
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

    public User.Role findByLoginPassword(String login, String password) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN_PASSWORD)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String pass = resultSet.getString(1);
                    if (BCrypt.checkpw(password, pass)) {
                        return User.Role.valueOf(resultSet.getString(2));
                    }
                }
            }
            return User.Role.GUEST;
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
    }

    public boolean checkLogin(String login) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
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

    public User findByLogin(String name) throws DaoException {
        User user = new User();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user.setUserId(resultSet.getLong(1));
                    user.setLogin(resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            log.error("SQLException :" + e);
            throw new DaoException("Dao statement fail" + e);
        }
        return user;
    }


}
