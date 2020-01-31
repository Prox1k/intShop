package mate.academy.internetshop.dao.jdbc;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
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
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static final Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);
    private static final String ITEMS_TABLE = "items";
    private static final String BUCKET_ITEM_TABLE = "bucket_item";
    private static final String ORDER_ITEM_TABLE = "order_items";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        String query = String.format("INSERT INTO %s (name, price) VALUES(?, ?)",
                ITEMS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't create item in BD", e);
        }
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        String query = String.format("SELECT * FROM %s WHERE item_id=?;", ITEMS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setItemId(rs.getLong("item_id"));
                return Optional.of(item);
            }
        } catch (SQLException e) {
            logger.warn("Can't find item with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) {
        String query = String.format("UPDATE %s SET name=? ,price=? "
                + "WHERE item_id=?;", ITEMS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setLong(3, item.getItemId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't find item with id = " + item.getItemId(), e);
        }
        return item;
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Item> item = get(id);
        if (item.isPresent()) {
            String query = String.format("DELETE FROM %s WHERE item_id=?", BUCKET_ITEM_TABLE);
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                logger.warn("Can't delete item by id in bucket_item");
            }

            query = String.format("DELETE FROM %s WHERE item_id=?", ORDER_ITEM_TABLE);
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                logger.warn("Can't delete item by id in order_item");
            }

            query = String.format("DELETE FROM %s WHERE item_id=?", ITEMS_TABLE);
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                logger.warn("Can't delete item by id");
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Item item) {
        return deleteById(item.getItemId());
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", ITEMS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setItemId(rs.getLong("item_id"));
                items.add(item);
            }
        } catch (SQLException e) {
            logger.error("Can't get all items ", e);
        }
        return items;
    }
}
