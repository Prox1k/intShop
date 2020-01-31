package mate.academy.internetshop.dao.jdbc;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoJdbcImpl.class);
    private static final String USERS_TABLE = "users";
    private static final String ROLES_TABLE = "roles";
    private static final String USER_ROLES_TABLE = "user_roles";
    private static final String BUCKET_TABLE = "bucket";
    private static final String ORDERS_TABLE = "orders";

    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static OrderDao orderDao;

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Optional<User> login(String login) {
        String query = String.format("SELECT users.user_id AS userId, name, password, token, login, "
                        + "roles.role_name AS role, roles.role_id AS roleId FROM %s INNER JOIN %s "
                        + "ON users.user_id=user_roles.user_id "
                        + "INNER JOIN %s ON user_roles.role_id = roles.role_id WHERE users.name=?",
                USERS_TABLE, USER_ROLES_TABLE, ROLES_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            User user = getUser(rs);
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            logger.warn("Can't login", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByToken(String token) {
        String query = String.format("SELECT users.user_id AS userId, name, password, token, login, "
                        + "roles.role_name AS role, roles.role_id AS roleId FROM %s INNER JOIN %s "
                        + "ON users.user_id=user_roles.user_id "
                        + "INNER JOIN %s ON user_roles.role_id = roles.role_id WHERE users.token=?",
                USERS_TABLE, USER_ROLES_TABLE, ROLES_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            User user = getUser(rs);
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            logger.warn("Can't find user by token", e);
        }
        return Optional.empty();
    }

    @Override
    public User create(User user) {
        String query = String.format("INSERT INTO %s(name, surname, login, password, token) " +
                        "VALUE(?, ?, ?, ?, ?)", USERS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getLogin());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getToken());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                user.setUserId(rs.getLong(1));
            }
        } catch (SQLException e) {
            logger.warn("Can't create user", e);
        }
        user.setRoles(getUserRoles(user));
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        String query = String.format("SELECT users.user_id AS userId, name, password, token, login, "
                        + "roles.role_name AS role, roles.role_id AS roleId FROM %s INNER JOIN %s "
                        + "ON users.user_id=user_roles.user_id "
                        + "INNER JOIN %s ON user_roles.role_id = roles.role_id WHERE users.user_id=?",
                USERS_TABLE, USER_ROLES_TABLE, ROLES_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            User user = getUser(rs);
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            logger.warn("Can't get user", e);
        }
        return Optional.empty();
    }

    @Override
    public User update(User user) {
        User newUser = user;
        String query = String.format("UPDATE %s(name, password, token) VALUE(?, ?, ?)",
                USERS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getToken());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't update user", e);
        }
        query = String.format("DELETE FROM %s WHERE user_id=?",
                USER_ROLES_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't delete roles", e);
        }
        newUser.setRoles(getUserRoles(user));
        return newUser;
    }

    @Override
    public boolean deleteById(Long id) {
        deleteByUserId(id, ORDERS_TABLE);
        deleteByUserId(id, BUCKET_TABLE);
        deleteByUserId(id, USER_ROLES_TABLE);
        deleteByUserId(id, USERS_TABLE);
        return true;
    }

    @Override
    public boolean delete(User user) {
        return deleteById(user.getUserId());
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String query = String.format("SELECT * FROM %s",
                USERS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setUserId(rs.getLong("user_id"));
                user.setRoles(getUserRoles(user));
                list.add(user);
            }
        } catch (SQLException e) {
            logger.warn("Can't get all users", e);
        }
        return list;
    }

    private User getUser(ResultSet rs) throws SQLException {
        User user = null;
        while (rs.next()) {
            user = new User();
            user.setName(rs.getString("name"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setToken(rs.getString("token"));
            user.setUserId(rs.getLong("userId"));
            Role role = new Role(Role.RoleName.valueOf(rs.getString("role")));
            role.setId(rs.getLong("roleId"));
            user.addRole(role);
        }
        return user;
    }

    private Set<Role> getUserRoles(User user) {
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            String query = String.format("SELECT role_id FROM %s WHERE role_name=?",
                    ROLES_TABLE);
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, role.getRoleName().toString());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    role.setId(rs.getLong("role_id"));
                }
            } catch (SQLException e) {
                logger.warn("Can't get roles id", e);
            }
            query = String.format("INSERT INTO %s (user_id, role_id) VALUES(?, ?)",
                    USER_ROLES_TABLE);
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setLong(1, user.getUserId());
                stmt.setLong(2, role.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                logger.warn("Can't insert roles for new user", e);
            }
        }
        return roles;
    }

    private void deleteByUserId(Long userId, String table) {
        String query = String.format("DELETE FROM %s WHERE user_id=?",
                table);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't delete user role by userId due delete user", e);
        }
    }
}
