package mate.academy.internetshop.service;

import mate.academy.internetshop.model.Item;

import java.util.Optional;

public interface ItemService {

    Item create(Item item);

    Optional<Item> get(Long id);

    Item update(Item item);

    void delete(Long id);
}
