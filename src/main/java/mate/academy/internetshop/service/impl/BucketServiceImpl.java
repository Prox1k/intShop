package mate.academy.internetshop.service.impl;

import java.util.List;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {

    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static ItemDao itemDao;

    @Override
    public Bucket create(Bucket bucket) {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long bucketId) {
        return bucketDao.get(bucketId).get();
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public boolean delete(Long id) {
        return bucketDao.delete(id);
    }

    @Override
    public boolean addItem(Bucket bucket, Item item) {
        Bucket newBucket = get(bucket.getBucketId());
        newBucket.getItems().add(item);
        bucketDao.update(newBucket);
        return true;
    }

    @Override
    public boolean deleteItem(Bucket bucket, Item item) {
        Bucket newBucket = get(bucket.getBucketId());
        List<Item> itemOfBucket = newBucket.getItems();
        itemOfBucket.remove(item);
        bucketDao.update(newBucket);
        return true;
    }

    @Override
    public boolean clear(Bucket bucket) {
        Bucket tempBucket = get(bucket.getBucketId());
        tempBucket.getItems().clear();
        bucketDao.update(tempBucket);
        return true;
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        return bucket.getItems();
    }
}
