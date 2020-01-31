package mate.academy.internetshop.factory;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.dao.jdbc.BucketDaoJdbcImpl;
import mate.academy.internetshop.dao.jdbc.ItemDaoJdbcImpl;
import mate.academy.internetshop.dao.jdbc.OrderDaoJdbcImpl;
import mate.academy.internetshop.dao.jdbc.UserDaoJdbcImpl;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.service.impl.BucketServiceImpl;
import mate.academy.internetshop.service.impl.ItemServiceImpl;
import mate.academy.internetshop.service.impl.OrderServiceImpl;
import mate.academy.internetshop.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Factory {
    private static Logger logger = Logger.getLogger(Factory.class);
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/internet_shop?" +
                    "user=root&password=mysqlroot123&serverTimezone=UTC");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Can't establish connection to our DB ", e);
        }

    }

    private static ItemDao itemDaoInstance;
    private static BucketDao bucketDaoInstance;
    private static OrderDao orderDaoInstance;
    private static UserDao userDaoInstance;

    private static ItemService itemServiceInstance;
    private static BucketService bucketServiceInstance;
    private static OrderService orderServiceInstance;
    private static UserService userServiceInstance;

    public static ItemDao getItemDao() {
        if (itemDaoInstance == null) {
            itemDaoInstance = new ItemDaoJdbcImpl(connection);
        }
        return itemDaoInstance;
    }

    public static BucketDao getBucketDao() {
        if (bucketDaoInstance == null) {
            bucketDaoInstance = new BucketDaoJdbcImpl(connection);
        }
        return bucketDaoInstance;
    }

    public static OrderDao getOrderDao() {
        if (orderDaoInstance == null) {
            orderDaoInstance = new OrderDaoJdbcImpl(connection);
        }
        return orderDaoInstance;
    }

    public static UserDao getUserDao() {
        if (userDaoInstance == null) {
            userDaoInstance = new UserDaoJdbcImpl(connection);
        }
        return userDaoInstance;
    }

    public static ItemService getItemService() {
        if (itemServiceInstance == null) {
            itemServiceInstance = new ItemServiceImpl();
        }
        return itemServiceInstance;
    }

    public static BucketService getBucketService() {
        if (bucketServiceInstance == null) {
            bucketServiceInstance = new BucketServiceImpl();
        }
        return bucketServiceInstance;
    }

    public static OrderService getOrderService() {
        if (orderServiceInstance == null) {
            orderServiceInstance = new OrderServiceImpl();
        }
        return orderServiceInstance;
    }

    public static UserService getUserService() {
        if (userServiceInstance == null) {
            userServiceInstance = new UserServiceImpl();
        }
        return userServiceInstance;
    }
}
