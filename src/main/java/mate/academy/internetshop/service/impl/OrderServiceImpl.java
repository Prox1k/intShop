package mate.academy.internetshop.service.impl;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private static OrderDao orderDao;

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Optional<Order> get(Long id) {
        return orderDao.get(id);
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public void delete(Long id) {
        orderDao.delete(id);
    }

    @Override
    public Order completeOrder(List<Item> items, User user) {
        Double allPrice = items.stream()
                .map(Item::getPrice)
                .mapToDouble(elem -> elem).sum();
        Order order = new Order(items, allPrice, user);
        return create(order);
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return Storage.orders.stream()
                .filter(order -> order.getUserId().equals(user.getUserId()))
                .collect(Collectors.toList());
    }
}
