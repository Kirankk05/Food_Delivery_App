package com.foodDelivery.daoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.foodDelivery.dao.OrderHistoryDAO;
import com.foodDelivery.model.OrderHistory;

public class OrderHistoryDAOImp implements OrderHistoryDAO {

	private static final String URL = "jdbc:mysql://localhost:3306/fooddeliveryapp";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	private static final String INSERT_QUERY = "INSERT INTO orderhistory (OrderHistoryID, UserID, OrderID, OrderDate, TotalAmount, Status) VALUES (?, ?, ?, ?, ?, ?)";

	private static final String SELECT_QUERY = "SELECT * FROM orderhistory WHERE OrderHistoryID = ?";

	private static final String UPDATE_QUERY = "UPDATE orderhistory SET UserID = ?, OrderID = ?, OrderDate = ?, TotalAmount = ?, Status = ? WHERE OrderHistoryID = ?";

	private static final String DELETE_QUERY = "DELETE FROM orderhistory WHERE OrderHistoryID = ?";

	private static final String SELECT_BY_USER_QUERY = "SELECT * FROM orderhistory WHERE UserID = ?";

	public OrderHistoryDAOImp() {
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
	public void addOrderHistory(OrderHistory orderHistory) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

			preparedStatement.setInt(1, orderHistory.getOrderHistoryId());
			preparedStatement.setInt(2, orderHistory.getUserId());
			preparedStatement.setInt(3, orderHistory.getOrderId());
			preparedStatement.setDate(4, new Date(orderHistory.getOrderDate().getTime()));
			preparedStatement.setDouble(5, orderHistory.getTotalAmount());
			preparedStatement.setString(6, orderHistory.getStatus());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public OrderHistory getOrderHistory(int orderHistoryId) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {

			preparedStatement.setInt(1, orderHistoryId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return new OrderHistory(resultSet.getInt("OrderHistoryID"), resultSet.getInt("UserID"),
							resultSet.getInt("OrderID"), resultSet.getDate("OrderDate"),
							resultSet.getDouble("TotalAmount"), resultSet.getString("Status"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateHistory(OrderHistory orderHistory) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {

			preparedStatement.setInt(1, orderHistory.getUserId());
			preparedStatement.setInt(2, orderHistory.getOrderId());
			preparedStatement.setDate(3, new Date(orderHistory.getOrderDate().getTime()));
			preparedStatement.setDouble(4, orderHistory.getTotalAmount());
			preparedStatement.setString(5, orderHistory.getStatus());
			preparedStatement.setInt(6, orderHistory.getOrderHistoryId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOrderHistory(int orderHistoryId) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {

			preparedStatement.setInt(1, orderHistoryId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<OrderHistory> getOrderHistoriesByUser(int userId) {
		List<OrderHistory> histories = new ArrayList<>();

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_QUERY)) {

			preparedStatement.setInt(1, userId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					OrderHistory orderHistory = new OrderHistory(resultSet.getInt("OrderHistoryID"),
							resultSet.getInt("UserID"), resultSet.getInt("OrderID"), resultSet.getDate("OrderDate"),
							resultSet.getDouble("TotalAmount"), resultSet.getString("Status"));
					histories.add(orderHistory);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return histories;
	}
}
