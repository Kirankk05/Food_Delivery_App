package com.foodDelivery.daoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foodDelivery.dao.MenuDAO;
import com.foodDelivery.model.Menu;

public class MenuDAOImp implements MenuDAO {

	private static final String URL = "jdbc:mysql://localhost:3306/fooddeliveryapp";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	private static final String INSERT_QUERY = "INSERT INTO menu (MenuID, RestaurantID, ItemName, Description, Price, IsAvailable) VALUES (?, ?, ?, ?, ?, ?)";

	private static final String SELECT_QUERY = "SELECT * FROM menu WHERE MenuID = ?";

	private static final String UPDATE_QUERY = "UPDATE menu SET RestaurantID = ?, ItemName = ?, Description = ?, Price = ?, IsAvailable = ? WHERE MenuID = ?";

	private static final String DELETE_QUERY = "DELETE FROM menu WHERE MenuID = ?";

	private static final String SELECT_ALL_BY_RESTAURANT_QUERY = "SELECT * FROM menu WHERE RestaurantID = ?";

	public MenuDAOImp() {
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
	public void addMenu(Menu menu) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_QUERY)) {

			ps.setInt(1, menu.getMenuId());
			ps.setInt(2, menu.getRestaurantId());
			ps.setString(3, menu.getItemName());
			ps.setString(4, menu.getDescription());
			ps.setDouble(5, menu.getPrice());
			ps.setBoolean(6, menu.isAvailabel());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Menu getMenu(int menuId) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_QUERY)) {

			ps.setInt(1, menuId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Menu(rs.getInt("MenuID"), rs.getInt("RestaurantID"), rs.getString("ItemName"),
							rs.getString("Description"), rs.getDouble("Price"), rs.getBoolean("IsAvailable"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateMenu(Menu menu) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {

			ps.setInt(1, menu.getRestaurantId());
			ps.setString(2, menu.getItemName());
			ps.setString(3, menu.getDescription());
			ps.setDouble(4, menu.getPrice());
			ps.setBoolean(5, menu.isAvailabel());
			ps.setInt(6, menu.getMenuId());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteMenu(int menuId) {
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {

			ps.setInt(1, menuId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Menu> getAllMenuByRestaurant(int restaurantId) {
		List<Menu> menus = new ArrayList<>();

		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_ALL_BY_RESTAURANT_QUERY)) {

			ps.setInt(1, restaurantId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Menu menu = new Menu(rs.getInt("MenuID"), rs.getInt("RestaurantID"), rs.getString("ItemName"),
							rs.getString("Description"), rs.getDouble("Price"), rs.getBoolean("IsAvailable"));
					menus.add(menu);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return menus;
	}
}
