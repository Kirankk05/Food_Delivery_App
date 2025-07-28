package com.foodDelivery.dao;

import java.util.List;

import com.foodDelivery.model.Menu;

public interface MenuDAO {
	void addMenu(Menu menu);
	Menu getMenu(int menuId);
	void updateMenu(Menu menu);
	void deleteMenu(int menuId);
	List<Menu> getAllMenuByRestaurant(int restaurantID);

}
