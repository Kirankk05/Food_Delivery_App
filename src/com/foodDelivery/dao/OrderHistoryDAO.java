package com.foodDelivery.dao;

import java.util.List;

import com.foodDelivery.model.OrderHistory;

public interface OrderHistoryDAO {
	
	void addOrderHistory(OrderHistory orderHistory);
	OrderHistory getOrderHistory(int orderHistory);
	void updateHistory(OrderHistory orderHistory);
	void deleteOrderHistory(int orderHistoryId);
	List<OrderHistory> getOrderHistoriesByUser(int userId);
	

}
