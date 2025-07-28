<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foodDelivery.model.Order" %>

<%
    // Only allow access if logged in (admin or restaurant owner)
    Object userObj = session.getAttribute("loggedInUser");
    if (userObj == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // The servlet/controller should set all orders in a request attribute
    List<Order> allOrders = (List<Order>) request.getAttribute("allOrders");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Orders - Food Delivery App</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 90%;
            margin: 30px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #fc8019;
            color: #fff;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .btn {
            padding: 8px 12px;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
        }
        .btn-delivered {
            background-color: #28a745;
        }
        .btn-delivered:hover {
            background-color: #218838;
        }
        .btn-disabled {
            background-color: #6c757d;
            cursor: not-allowed;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Manage Orders</h2>

    <% if (allOrders == null || allOrders.isEmpty()) { %>
        <p>No orders found.</p>
    <% } else { %>
        <table>
            <tr>
                <th>Order ID</th>
                <th>User ID</th>
                <th>Restaurant ID</th>
                <th>Order Date</th>
                <th>Payment Method</th>
                <th>Status</th>
                <th>Total Amount (₹)</th>
                <th>Action</th>
            </tr>
            <% for (Order order : allOrders) { %>
                <tr>
                    <td><%= order.getOrderId() %></td>
                    <td><%= order.getUserId() %></td>
                    <td><%= order.getRestaurantId() %></td>
                    <td><%= order.getOrderDate() %></td>
                    <td><%= order.getPaymentMethod() %></td>
                    <td><%= order.getStatus() %></td>
                    <td>₹<%= String.format("%.2f", order.getTotalAmount()) %></td>
                    <td>
                        <% if ("Pending".equalsIgnoreCase(order.getStatus())) { %>
                            <form action="updateOrderStatus" method="post" style="display:inline;">
                                <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
                                <input type="hidden" name="status" value="Delivered">
                                <button type="submit" class="btn btn-delivered">Mark as Delivered</button>
                            </form>
                        <% } else { %>
                            <button class="btn btn-disabled" disabled>Delivered</button>
                        <% } %>
                    </td>
                </tr>
            <% } %>
        </table>
    <% } %>
</div>

</body>
</html>
