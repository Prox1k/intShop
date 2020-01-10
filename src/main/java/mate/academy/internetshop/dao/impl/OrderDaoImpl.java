package mate.academy.internetshop.dao.impl;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.IdGenerator;
import mate.academy.internetshop.model.Order;

import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        order.setOrderId(IdGenerator.getOrderId());
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Optional.ofNullable(Storage.orders
                .stream()
                .filter(order -> order.getOrderId().equals(id))
                .findFirst()
                .orElseThrow(()
                        -> new NoSuchElementException("Can't find order with id "
                        + id)));
    }

    @Override
    public Order update(Order order) {
        Optional<Order> updatedOrderOptional = get(order.getOrderId());
        if (updatedOrderOptional.isPresent()) {
            Order updatedOrder = updatedOrderOptional.get();
            updatedOrder.setItems(order.getItems());
            updatedOrder.setOrderId(order.getOrderId());
            updatedOrder.setUserId(order.getUserId());
            updatedOrder.setAllPrice(order.getAllPrice());
            return updatedOrder;
        }
        return order;
    }

    @Override
    public boolean delete(Long id) {
        Optional<Order> deletedOrderOptional = get(id);
        if (deletedOrderOptional.isPresent()) {
            Order deletedOrder = deletedOrderOptional.get();
            Storage.orders.removeIf(order -> order.getOrderId().equals(deletedOrder.getOrderId()));
            return true;
        }
        return false;
    }
}
