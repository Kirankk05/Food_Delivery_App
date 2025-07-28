package com.foodDelivery.daoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foodDelivery.dao.RestaurantDAO;
import com.foodDelivery.model.Restaurant;

public class RestaurantDAOImp implements RestaurantDAO {

	private static final String URL = "jdbc:mysql://localhost:3306/fooddeliveryapp";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	private static final String INSERT_QUERY = "INSERT INTO restaurant (RestaurantID, Name, CuisineType, DeliveryTime, Address, AdminUserID, Rating, IsActive, ImagePath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_QUERY = "UPDATE restaurant SET Name = ?, CuisineType = ?, DeliveryTime = ?, Address = ?, AdminUserID = ?, Rating = ?, IsActive = ?, ImagePath = ? WHERE RestaurantID = ?";

	private static final String SELECT_QUERY = "SELECT * FROM restaurant WHERE RestaurantID = ?";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM restaurant";
	private static final String DELETE_QUERY = "DELETE FROM restaurant WHERE RestaurantID = ?";

	public RestaurantDAOImp() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}

	@Override
	public void addRestaurant(Restaurant restaurant) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

			preparedStatement.setInt(1, restaurant.getRestaurantId());
			preparedStatement.setString(2, restaurant.getName());
			preparedStatement.setString(3, restaurant.getCuisineType());
			preparedStatement.setInt(4, restaurant.getDeliveryTime());
			preparedStatement.setString(5, restaurant.getAddress());
			preparedStatement.setInt(6, restaurant.getAdminUserId());
			preparedStatement.setDouble(7, restaurant.getRating());
			preparedStatement.setBoolean(8, restaurant.isActive());
			preparedStatement.setString(9, restaurant.getImagePath());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Restaurant getRestaurant(int restaurantId) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {

			preparedStatement.setInt(1, restaurantId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return new Restaurant(resultSet.getInt("RestaurantID"), resultSet.getString("Name"),
							resultSet.getString("CuisineType"), resultSet.getInt("DeliveryTime"),
							resultSet.getString("Address"), resultSet.getInt("AdminUserID"),
							resultSet.getDouble("Rating"), resultSet.getBoolean("IsActive"),
							resultSet.getString("ImagePath"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateRestaurant(Restaurant restaurant) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {

			preparedStatement.setString(1, restaurant.getName());
			preparedStatement.setString(2, restaurant.getCuisineType());
			preparedStatement.setInt(3, restaurant.getDeliveryTime());
			preparedStatement.setString(4, restaurant.getAddress());
			preparedStatement.setInt(5, restaurant.getAdminUserId());
			preparedStatement.setDouble(6, restaurant.getRating());
			preparedStatement.setBoolean(7, restaurant.isActive());
			preparedStatement.setString(8, restaurant.getImagePath());
			preparedStatement.setInt(9, restaurant.getRestaurantId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRestaurant(int restaurantId) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {

			preparedStatement.setInt(1, restaurantId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Restaurant> getAllRestaurants() {
		List<Restaurant> restaurants = new ArrayList<>();

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Restaurant restaurant = new Restaurant(
					    resultSet.getInt("RestaurantID"),
					    resultSet.getString("Name"),
					    resultSet.getString("CuisineType"),
					    resultSet.getInt("DeliveryTime"), 
					    resultSet.getString("Address"),
					    resultSet.getInt("AdminUserID"),
					    resultSet.getDouble("Rating"),
					    resultSet.getBoolean("IsActive"),
					    resultSet.getString("ImagePath")
					);
					restaurants.add(restaurant);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return restaurants;
	}
}
