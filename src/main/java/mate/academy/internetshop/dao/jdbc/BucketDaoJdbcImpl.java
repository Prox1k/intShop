package mate.academy.internetshop.dao.jdbc;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;
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
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {
    private static final Logger logger = Logger.getLogger(BucketDaoJdbcImpl.class);
    private static final String BUCKET_TABLE = "bucket";
    private static final String BUCKET_ITEMS_TABLE = "bucket_item";
    private static final String ITEMS_TABLE = "items";

    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) {
        String query = String.format("INSERT INTO %s(user_id) VALUE(?)",
                BUCKET_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, bucket.getUserId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    bucket.setBucketId(rs.getLong(1));
                } else {
                    throw new SQLException("Creating bucket failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.warn("Can't create bucket in BD", e);
        }
        insertIntoBucketItemTable(bucket);
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long id) {
        String query = String.format("SELECT bucket_id, user_id FROM %s WHERE bucket_id=?;",
                BUCKET_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            Bucket bucket = null;
            while (rs.next()) {
                bucket = new Bucket(rs.getLong(2));
                bucket.setBucketId(rs.getLong(1));
                bucket.setItems(getAllItemFromBucket(bucket.getBucketId()));
                return Optional.of(bucket);
            }
        } catch (SQLException e) {
            logger.warn("Can't find bucket with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Bucket update(Bucket bucket) {
        String query = String.format("DELETE FROM %s WHERE bucket_id=?",
                BUCKET_ITEMS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, bucket.getBucketId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't update bucket", e);
        }
        insertIntoBucketItemTable(bucket);
        return bucket;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = String.format("DELETE FROM %s WHERE bucket_id=?",
                BUCKET_ITEMS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't delete bucket by id = " + id, e);
        }
        query = String.format("DELETE FROM %s WHERE bucket_id=?",
                BUCKET_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.warn("Can't delete bucket by id = " + id, e);
        }
        return false;
    }

    @Override
    public boolean delete(Bucket bucket) {
        return deleteById(bucket.getBucketId());
    }

    @Override
    public List<Bucket> getAll() {
        List<Bucket> tempBuckets = new ArrayList<>();
        String query = String.format("SELECT bucket_id, user_id FROM %s",
                BUCKET_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bucket bucket = new Bucket(rs.getLong(2));
                bucket.setBucketId(rs.getLong(1));
                bucket.setItems(getAllItemFromBucket(rs.getLong(1)));
                tempBuckets.add(bucket);
            }
        } catch (SQLException e) {
            logger.warn("Can't get all buckets", e);
        }
        return tempBuckets;
    }

    private List<Item> getAllItemFromBucket(Long bucketId) {
        List<Item> listOfItems = new ArrayList<>();
        String query = String.format("SELECT items.item_id, name, price FROM %s items JOIN %s bit"
                        + " ON items.item_id = bit.item_id AND bucket_id = ?",
                ITEMS_TABLE, BUCKET_ITEMS_TABLE);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, bucketId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getString(2), rs.getDouble(3));
                item.setItemId(rs.getLong(1));
                listOfItems.add(item);
            }
        } catch (SQLException e) {
            logger.warn("Can't get all items from bucket", e);
        }
        return listOfItems;
    }

    @Override
    public void clear(Bucket bucket) {
        String query = String.format("DELETE FROM %s WHERE bucket_id=%d",
                BUCKET_ITEMS_TABLE, bucket.getBucketId());
        try (Statement ps = connection.createStatement()) {
            ps.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Can't clear bucket", e);
        }
    }

    private void insertIntoBucketItemTable(Bucket bucket) {
        for (Item item : bucket.getItems()) {
            String queryItems = String.format("INSERT INTO %s(bucket_id, item_id) VALUE(?, ?)",
                    BUCKET_ITEMS_TABLE);
            try (PreparedStatement preparedStatement = connection.prepareStatement(queryItems)) {
                preparedStatement.setLong(1, bucket.getBucketId());
                preparedStatement.setLong(2, item.getItemId());
                int rows = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                logger.warn("Can't add item_id/bucket_id to bucket_item", e);
            }
        }
    }
}
