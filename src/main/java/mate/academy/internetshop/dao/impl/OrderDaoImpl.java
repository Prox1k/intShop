package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.IdGenerator;
import mate.academy.internetshop.model.Order;

public class OrderDaoImpl  {

    public Order create(Order order) {
        order.setOrderId(IdGenerator.incOrderId());
        Storage.orders.add(order);
        return order;
    }

    public Optional<Order> get(Long orderId) {
        return Storage.orders
                .stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst();
    }

    public Order update(Order order) {
        Optional<Order> updatedOrderOptional = get(order.getOrderId());
        Order updatedOrder = updatedOrderOptional.orElseThrow(NoSuchElementException::new);
        updatedOrder.setItems(order.getItems());
        updatedOrder.setOrderId(order.getOrderId());
        updatedOrder.setUserId(order.getUserId());
        updatedOrder.setAllPrice(order.getAllPrice());
        return updatedOrder;
    }

    public boolean deleteById(Long orderId) {
        return Storage.orders.removeIf(o -> o.getOrderId().equals(orderId));
    }

    public boolean delete(Order order) {
        return Storage.orders.remove(order);
    }

    public List<Order> getAll() {
        return Storage.orders;
    }
}
