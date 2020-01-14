package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.IdGenerator;
import mate.academy.internetshop.model.Order;

@Dao
public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        order.setOrderId(IdGenerator.incOrderId());
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long orderId) {
        return Storage.orders
                .stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst();
    }

    @Override
    public Order update(Order order) {
        Optional<Order> updatedOrderOptional = get(order.getOrderId());
        Order updatedOrder = updatedOrderOptional.orElseThrow(NoSuchElementException::new);
        updatedOrder.setItems(order.getItems());
        updatedOrder.setOrderId(order.getOrderId());
        updatedOrder.setUserId(order.getUserId());
        updatedOrder.setAllPrice(order.getAllPrice());
        return updatedOrder;
    }

    @Override
    public boolean deleteById(Long orderId) {
        Storage.orders.removeIf(o -> o.getOrderId().equals(orderId));
        return true;
    }

    @Override
    public boolean delete(Order order) {
        return Storage.orders.remove(order);
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }
}
