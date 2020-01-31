package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.IdGenerator;

public class BucketDaoImpl  {
    public Bucket create(Bucket bucket) {
        bucket.setBucketId(IdGenerator.incBucketId());
        Storage.buckets.add(bucket);
        return bucket;
    }

    public Optional<Bucket> get(Long bucketId) {
        return Storage.buckets
                .stream()
                .filter(bucket -> bucket.getBucketId().equals(bucketId))
                .findFirst();
    }

    public Bucket update(Bucket bucket) {
        Optional<Bucket> updatedBucketOptional = get(bucket.getBucketId());
        Bucket updatedBucket = updatedBucketOptional.orElseThrow(NoSuchElementException::new);
        updatedBucket.setUserId(bucket.getUserId());
        updatedBucket.setItems(bucket.getItems());
        updatedBucket.setBucketId(bucket.getBucketId());
        return updatedBucket;
    }

    public boolean deleteById(Long bucketId) {
        return Storage.buckets.removeIf(bucket -> bucket.getBucketId().equals(bucketId));
    }

    public boolean delete(Bucket bucket) {
        return Storage.buckets.remove(bucket);
    }

    public List<Bucket> getAll() {
        return Storage.buckets;
    }

    public void clear(Bucket bucket) {
    }
}
