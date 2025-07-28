package com.foodDelivery.daoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foodDelivery.dao.OrderItemDAO;
import com.foodDelivery.model.OrderItem;

public class OrderItemDAOImp implements OrderItemDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/fooddeliveryapp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static final String INSERT_QUERY =
        "INSERT INTO orderitem (OrderItemID, OrderID, MenuID, Quantity, ItemTotal) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_QUERY =
        "SELECT * FROM orderitem WHERE OrderItemID = ?";

    private static final String UPDATE_QUERY =
        "UPDATE orderitem SET OrderID = ?, MenuID = ?, Quantity = ?, ItemTotal = ? WHERE OrderItemID = ?";

    private static final String DELETE_QUERY =
        "DELETE FROM orderitem WHERE OrderItemID = ?";

    private static final String SELECT_BY_ORDER_ID_QUERY =
        "SELECT * FROM orderitem WHERE OrderID = ?";

    public OrderItemDAOImp() {
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
    public void addOrderItem(OrderItem orderItem) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

            preparedStatement.setInt(1, orderItem.getOrderItemId());
            preparedStatement.setInt(2, orderItem.getOrderId());
            preparedStatement.setInt(3, orderItem.getMenuId());
            preparedStatement.setInt(4, orderItem.getQuantity());
            preparedStatement.setDouble(5, orderItem.getItemTotal());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrderItem getOrderItem(int orderItemId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {

            preparedStatement.setInt(1, orderItemId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new OrderItem(
                        resultSet.getInt("OrderItemID"),
                        resultSet.getInt("OrderID"),
                        resultSet.getInt("MenuID"),
                        resultSet.getInt("Quantity"),
                        resultSet.getDouble("ItemTotal")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {

            preparedStatement.setInt(1, orderItem.getOrderId());
            preparedStatement.setInt(2, orderItem.getMenuId());
            preparedStatement.setInt(3, orderItem.getQuantity());
            preparedStatement.setDouble(4, orderItem.getItemTotal());
            preparedStatement.setInt(5, orderItem.getOrderItemId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderItem(int orderItemId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {

            preparedStatement.setInt(1, orderItemId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderItem> getOrderItemByOrder(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ORDER_ID_QUERY)) {

            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderItem orderItem = new OrderItem(
                        resultSet.getInt("OrderItemID"),
                        resultSet.getInt("OrderID"),
                        resultSet.getInt("MenuID"),
                        resultSet.getInt("Quantity"),
                        resultSet.getDouble("ItemTotal")
                    );
                    orderItems.add(orderItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItems;
    }
}
