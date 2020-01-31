package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.IdGenerator;
import mate.academy.internetshop.model.Item;

public class ItemDaoImpl {
    public Item create(Item item) {
        item.setItemId(IdGenerator.incItemId());
        Storage.items.add(item);
        return item;
    }

    public Optional<Item> get(Long itemId) {
        return Storage.items
                .stream()
                .filter(item -> item.getItemId().equals(itemId))
                .findFirst();
    }

    public Item update(Item item) {
        Optional<Item> updatedItemOptional = get(item.getItemId());
        Item updatedItem = updatedItemOptional.orElseThrow(NoSuchElementException::new);
        updatedItem.setPrice(item.getPrice());
        updatedItem.setItemId(item.getItemId());
        updatedItem.setName(item.getName());
        return updatedItem;
    }

    public boolean deleteById(Long itemId) {
        return Storage.items.removeIf(i -> i.getItemId().equals(itemId));
    }

    public boolean delete(Item item) {
        return Storage.items.remove(item);
    }

    public List<Item> getAll() {
        return Storage.items;
    }
}
