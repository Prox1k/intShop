package mate.academy.internetshop.service;

import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order create(Order order);

    Optional<Order> get(Long id);

    Order update(Order order);

    void delete(Long id);

    Order completeOrder(List<Item> items, User user);

    List<Order> getUserOrders(User user);

}
