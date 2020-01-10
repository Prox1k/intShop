package mate.academy.internetshop.dao.impl;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.IdGenerator;
import mate.academy.internetshop.model.Item;

import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class ItemDaoImpl implements ItemDao {
    @Override
    public Item create(Item item) {
        item.setItemId(IdGenerator.getItemId());
        Storage.items.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        return Optional.ofNullable(Storage.items
                .stream()
                .filter(item -> item.getItemId().equals(id))
                .findFirst()
                .orElseThrow(()
                        -> new NoSuchElementException("Can't find item with id " + id)));
    }

    @Override
    public Item update(Item item) {
        Optional<Item> updatedItemOptional = get(item.getItemId());
        if (updatedItemOptional.isPresent()) {
            Item updatedItem = updatedItemOptional.get();
            updatedItem.setPrice(item.getPrice());
            updatedItem.setItemId(item.getItemId());
            updatedItem.setName(item.getName());
            return updatedItem;
        }
        return item;
    }

    @Override
    public boolean delete(Long id) {
        Optional<Item> deletedItemOptional = get(id);
        if (deletedItemOptional.isPresent()) {
            Item deletedItem = deletedItemOptional.get();
            Storage.items.removeIf(item -> item.equals(deletedItem));
            return true;
        }
        return false;
    }
}
