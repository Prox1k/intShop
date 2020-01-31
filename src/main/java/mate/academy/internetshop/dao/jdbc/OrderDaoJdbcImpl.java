package mate.academy.internetshop.dao.jdbc;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {
    private static final Logger logger = Logger.getLogger(OrderDaoJdbcImpl.class);
    private static final String ORDERS_TABLE = "orders";
    private static final String ORDER_ITEMS_TABLE = "order_items";
    private static final String ITEMS_TABLE = "items";

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order order) {
        String query = String.format("INSERT INTO %s (user_id, amount) VALUES(?, ?)", ORDERS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, order.getUserId());
            ps.setDouble(2, order.getAllPrice());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    order.setOrderId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            logger.warn("Can't create order in BD", e);
        }
        insertIntoOrderItemTable(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = String.format("SELECT order_id, user_id, amount "
                + "FROM %s WHERE order_id=(?);", ORDERS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getLong(1));
                order.setUserId(rs.getLong(2));
                order.setAllPrice(rs.getDouble(3));
                order.setItems(getAllItemFromOrder(id));
            }
        } catch (SQLException e) {
            logger.warn("Can't get order by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Order update(Order order) {
        String query = String.format("DELETE FROM %s WHERE order_id=?",
                ORDER_ITEMS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, order.getOrderId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't update order(DELETE)", e);
        }
        insertIntoOrderItemTable(order);
        query = String.format("UPDATE %s SET amount=(?) WHERE order_id=?",
                ORDERS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDouble(1, order.getAllPrice());
            ps.setLong(2, order.getOrderId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't update order(UPDATE)", e);
        }
        return order;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = String.format("DELETE FROM %s WHERE order_id =?",
                ORDER_ITEMS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't delete order by id(order_items) " + id, e);
        }

        query = String.format("DELETE FROM %s WHERE order_id =?",
                ORDERS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.warn("Can't delete order by id(orders) " + id, e);
        }
        return false;
    }

    @Override
    public boolean delete(Order order) {
        return deleteById(order.getOrderId());
    }

    @Override
    public List<Order> getAll() {
        List<Order> tempOrders = new ArrayList<>();
        String query = String.format("SELECT order_id, user_id, amount FROM %s",
                ORDERS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getLong(1));
                order.setUserId(rs.getLong(2));
                order.setAllPrice(rs.getDouble(3));
                order.setItems(getAllItemFromOrder(rs.getLong(1)));
                tempOrders.add(order);
            }
        } catch (SQLException e) {
            logger.warn("Can't get all orders", e);
        }
        List<Order> orders = new ArrayList<>();
        for (Order order : tempOrders) {
            orders.add(get(order.getOrderId()).get());
        }
        return orders;
    }

    private List<Item> getAllItemFromOrder(Long orderId)  {
        List<Item> listOfItems = new ArrayList<>();
        String query = String.format("SELECT items.item_id, name, price FROM %s items JOIN %s "
                        + "ON items.item_id = oit.item_id AND order_id = ?",
                ITEMS_TABLE, ORDER_ITEMS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getString(2), rs.getDouble(3));
                item.setItemId(rs.getLong(1));
                listOfItems.add(item);
            }
        } catch (SQLException e) {
            logger.warn("Can't get all items from order", e);
        }
        return listOfItems;
    }

    private void insertIntoOrderItemTable(Order order) {
        String query = String.format("INSERT INTO %s(order_id, item_id) VALUE(?, ?)",
                ORDER_ITEMS_TABLE);
        for (Item item : order.getItems()) {
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setLong(1, order.getOrderId());
                ps.setLong(2, item.getItemId());
                int rows = ps.executeUpdate();
            } catch (SQLException e) {
                logger.warn("Can't insert new items to order_items", e);
            }
        }
    }
}
