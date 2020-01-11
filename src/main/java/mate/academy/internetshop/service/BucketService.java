package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public interface BucketService {

    Bucket create(Bucket bucket);

    Bucket get(Long bucketId);

    Bucket update(Bucket bucket);

    boolean delete(Long id);

    boolean addItem(Bucket bucket, Item item);

    boolean deleteItem(Bucket bucket, Item item);

    boolean clear(Bucket bucket);

    List<Item> getAllItems(Bucket bucket);
}
