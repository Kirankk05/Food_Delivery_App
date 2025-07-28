<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.foodDelivery.model.Order" %>
<%@ page import="com.foodDelivery.model.CartItem" %>
<%@ page import="java.util.Map" %>
<%
    Order order = (Order) session.getAttribute("order");
    Map<Integer, CartItem> orderedItems = null;

    if (order == null) {
        response.sendRedirect("home.jsp");
        return;
    }

    Object cartItemsObj = session.getAttribute("orderedItems");
    if (cartItemsObj != null && cartItemsObj instanceof Map) {
        orderedItems = (Map<Integer, CartItem>) cartItemsObj;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Confirmation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fff8f0;
            margin: 0;
            padding: 20px;
        }
        .confirmation-container {
            max-width: 700px;
            margin: 0 auto;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            text-align: center;
        }
        h2 {
            color: #2ecc71;
        }
        p {
            font-size: 16px;
            margin: 10px 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }
        .home-btn {
            margin-top: 30px;
            background-color: #fc8019;
            color: white;
            padding: 10px 20px;
            border: none;
            font-weight: bold;
            border-radius: 6px;
            cursor: pointer;
            text-decoration: none;
        }
        .home-btn:hover {
            background-color: #e86f0f;
        }
    </style>
</head>
<body>

<div class="confirmation-container">
    <h2>Order Placed Successfully!</h2>
    <p><strong>Order ID:</strong> <%= order.getOrderId() %></p>
    <p><strong>Payment Method:</strong> <%= order.getPaymentMethod() %></p>
    <p><strong>Total Amount:</strong> ₹<%= order.getTotalAmount() %></p>
    <p><strong>Status:</strong> <%= order.getStatus() %></p>
    <p><strong>Estimated Delivery:</strong> 30–45 minutes</p>

    <% if (orderedItems != null && !orderedItems.isEmpty()) { %>
        <h3>Ordered Items</h3>
        <table>
            <tr>
                <th>Item Name</th>
                <th>Price (₹)</th>
                <th>Quantity</th>
                <th>Total</th>
            </tr>
            <% for (CartItem item : orderedItems.values()) { %>
                <tr>
                    <td><%= item.getName() %></td>
                    <td><%= item.getPrice() %></td>
                    <td><%= item.getQuantity() %></td>
                    <td><%= item.getPrice() * item.getQuantity() %></td>
                </tr>
            <% } %>
        </table>
    <% } %>

    <p>Thank you for ordering from our app!</p>

    <a href="home.jsp" class="home-btn">Back to Home</a>
</div>

</body>
</html>
