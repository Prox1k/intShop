package mate.academy.internetshop.dao.jdbc;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);
    private static String DB_NAME = "internet_shop";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        String name = item.getName();
        Double price = item.getPrice();
        String query = String.format("INSERT INTO %s.items (name, price) VALUES('%s', %.0f)",
                DB_NAME, name, price);
        try (Statement stmt = connection.createStatement()) {
            int rs = stmt.executeUpdate(query);
            logger.info("Created row(s) :" + rs);
        } catch (SQLException e) {
            logger.error("Connection failed", e);
        }
        return null;
    }

    @Override
    public Optional<Item> get(Long id) {
        String query = String.format("SELECT * FROM %s.items WHERE item_id=%d;", DB_NAME, id);
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item();
                item.setName(name);
                item.setPrice(price);
                item.setItemId(itemId);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            logger.warn("Can't find item with id = " + id);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) {
        String query = String.format("update %s.items set name='%s' ,price=%.0f "
                + "where item_id=%s;", DB_NAME, item.getName(), item.getPrice(), item.getItemId());
        try (Statement stmt = connection.createStatement()) {
            int rs = stmt.executeUpdate(query);
            logger.info("Updated row(s) :" + rs);
        } catch (SQLException e) {
            logger.warn("Can't find item with id = " + item.getItemId());
        }
        return item;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = String.format("delete from %s.items where item_id=%d;",
                DB_NAME, id);
        try (Statement stmt = connection.createStatement()) {
            int rs = stmt.executeUpdate(query);
            logger.info("Deleted row(s) :" + rs);
            return true;
        } catch (SQLException e) {
            logger.warn("Can't find item with id = " + id);
            return false;
        }
    }

    @Override
    public boolean delete(Item item) {
        String query = String.format("delete from %s.items where item_id=%d;",
                DB_NAME, item.getItemId());
        try (Statement stmt = connection.createStatement()) {
            int rs = stmt.executeUpdate(query);
            logger.info("Deleted row(s) :" + rs);
            return true;
        } catch (SQLException e) {
            logger.warn("Can't find item with id = " + item.getItemId());
            return false;
        }
    }

    @Override
    public List<Item> getAll() {
        List<Item> allItems = new ArrayList<>();
        String query = String.format("select * from %s.items", DB_NAME);
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item();
                item.setName(name);
                item.setPrice(price);
                item.setItemId(itemId);
                allItems.add(item);
            }
            return allItems;
        } catch (SQLException e) {
            logger.error("Connection failed", e);
        }
        return allItems;
    }
}
