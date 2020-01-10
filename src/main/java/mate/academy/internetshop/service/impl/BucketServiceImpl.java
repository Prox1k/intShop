package mate.academy.internetshop.service.impl;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;

import java.util.List;
import java.util.Optional;

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
    public Optional<Bucket> get(Long bucketId) {
        return bucketDao.get(bucketId);
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public void delete(Long id) {
        bucketDao.delete(id);
    }

    @Override
    public void addItem(Bucket bucket, Item item) {
        Bucket newBucket = bucketDao.get(bucket.getBucketId()).get();
        newBucket.getItems().add(item);
        bucketDao.update(newBucket);
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) {
        Bucket newBucket = bucketDao.get(bucket.getBucketId()).get();
        List<Item> itemOfBucket = newBucket.getItems();
        itemOfBucket.remove(item);
        bucketDao.update(newBucket);
    }

    @Override
    public void clear(Bucket bucket) {
        Bucket tempBucket = bucketDao.get(bucket.getBucketId()).get();
        tempBucket.getItems().clear();
        bucketDao.update(tempBucket);
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        return bucketDao.get(bucket.getBucketId()).get().getItems();
    }
}
