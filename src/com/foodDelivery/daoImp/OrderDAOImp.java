package com.foodDelivery.daoImp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foodDelivery.dao.OrderDAO;
import com.foodDelivery.model.Order;
import com.foodDelivery.model.OrderItem;

public class OrderDAOImp implements OrderDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/fooddeliveryapp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // Insert Order (Auto-increment OrderID)
    private static final String INSERT_QUERY = 
        "INSERT INTO `ordertable` (UserID, RestaurantID, OrderDate, TotalAmount, Status, PaymentMethod) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String INSERT_ORDER_ITEM = 
        "INSERT INTO `orderitem` (OrderID, MenuID, Quantity, ItemTotal) VALUES (?, ?, ?, ?)";

    private static final String SELECT_QUERY = "SELECT * FROM `ordertable` WHERE OrderID = ?";
    private static final String UPDATE_QUERY = 
        "UPDATE `ordertable` SET UserID = ?, RestaurantID = ?, OrderDate = ?, TotalAmount = ?, Status = ?, PaymentMethod = ? WHERE OrderID = ?";
    private static final String DELETE_QUERY = "DELETE FROM `ordertable` WHERE OrderID = ?";
    private static final String SELECT_ALL_BY_USER_QUERY = "SELECT * FROM `ordertable` WHERE UserID = ?";

    public OrderDAOImp() {
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
    public int addOrder(Order order) {
        int orderId = 0;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getRestaurantId());
            ps.setDate(3, new Date(order.getOrderDate().getTime()));
            ps.setDouble(4, order.getTotalAmount());
            ps.setString(5, order.getStatus());
            ps.setString(6, order.getPaymentMethod());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    @Override
    public void addOrderItems(int orderId, List<OrderItem> items) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_ORDER_ITEM)) {

            for (OrderItem item : items) {
                ps.setInt(1, orderId);
                ps.setInt(2, item.getMenuId());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getItemTotal());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order getOrder(int orderId) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_QUERY)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                            rs.getInt("OrderID"),
                            rs.getInt("UserID"),
                            rs.getInt("RestaurantID"),
                            rs.getDate("OrderDate"),
                            rs.getDouble("TotalAmount"),
                            rs.getString("Status"),
                            rs.getString("PaymentMethod")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOrder(Order order) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {

            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getRestaurantId());
            ps.setDate(3, new Date(order.getOrderDate().getTime()));
            ps.setDouble(4, order.getTotalAmount());
            ps.setString(5, order.getStatus());
            ps.setString(6, order.getPaymentMethod());
            ps.setInt(7, order.getOrderId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(int orderId) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {

            ps.setInt(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getAllOrdersByUser(int userId) {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_BY_USER_QUERY)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("OrderID"));
                    order.setUserId(rs.getInt("UserID"));
                    order.setRestaurantId(rs.getInt("RestaurantID"));
                    order.setOrderDate(rs.getDate("OrderDate"));
                    order.setTotalAmount(rs.getDouble("TotalAmount"));
                    order.setStatus(rs.getString("Status"));
                    order.setPaymentMethod(rs.getString("PaymentMethod"));

                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

	@Override
	public List<Order> getAllOrders() {
		
		List<Order> orders = new ArrayList<>();
	    String sql = "SELECT * FROM `ordertable` ORDER BY OrderDate DESC";

	    try (Connection connection = getConnection();
	         PreparedStatement ps = connection.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Order order = new Order();
	            order.setOrderId(rs.getInt("OrderID"));
	            order.setUserId(rs.getInt("UserID"));
	            order.setRestaurantId(rs.getInt("RestaurantID"));
	            order.setOrderDate(rs.getDate("OrderDate"));
	            order.setTotalAmount(rs.getDouble("TotalAmount"));
	            order.setStatus(rs.getString("Status"));
	            order.setPaymentMethod(rs.getString("PaymentMethod"));
	            orders.add(order);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return orders;
	}
}
