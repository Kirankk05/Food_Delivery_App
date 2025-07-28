package com.foodDelivery.dao;

import java.util.List;
import com.foodDelivery.model.Order;
import com.foodDelivery.model.OrderItem;

public interface OrderDAO {
    int addOrder(Order order);                     // returns generated OrderID
    void addOrderItems(int orderId, List<OrderItem> items);
    Order getOrder(int orderId);
    void updateOrder(Order order);
    void deleteOrder(int orderId);
    List<Order> getAllOrdersByUser(int userID);
    List<Order> getAllOrders();

}
