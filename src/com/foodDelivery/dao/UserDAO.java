package com.foodDelivery.dao;

import java.util.List;

import com.foodDelivery.model.User;

public interface UserDAO {
	void addUser(User user);
	User getUser(int userId);
	void updateUser(User user);
	void deleteUser(int UserId);
	List<User> getAllUsers();
	User getUserByUsername(String username);
	User getUserByEmail(String email);


}
