package mate.academy.internetshop.service;

import mate.academy.internetshop.model.Item;

public interface ItemService {

    Item create(Item item);

    Item get(Long id);

    Item update(Item item);

    boolean delete(Long id);
}
