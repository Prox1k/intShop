package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public interface BucketService extends GenericService<Bucket, Long> {
    boolean addItem(Bucket bucket, Item item);

    boolean deleteItem(Bucket bucket, Item item);

    boolean clear(Bucket bucket);

    List<Item> getAllItems(Bucket bucket);

    Bucket getByUserId(Long userId);
}
