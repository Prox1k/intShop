package mate.academy.internetshop.dao.impl;

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
        order.setOrderId(IdGenerator.getOrderId());
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
        Order updatedOrder = updatedOrderOptional.get();
        updatedOrder.setItems(order.getItems());
        updatedOrder.setOrderId(order.getOrderId());
        updatedOrder.setUserId(order.getUserId());
        updatedOrder.setAllPrice(order.getAllPrice());
        return updatedOrder;
    }

    @Override
    public boolean delete(Long orderId) {
        Storage.orders.removeIf(o -> o.getOrderId().equals(orderId));
        return true;
    }
}
