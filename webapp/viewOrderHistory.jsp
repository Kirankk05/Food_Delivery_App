<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foodDelivery.model.Order" %>

<%
    // Get logged-in user
    Object userObj = session.getAttribute("loggedInUser");
    if (userObj == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Get order history from request (set by a servlet)
    List<Order> orderHistory = (List<Order>) request.getAttribute("orderHistory");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Order History - Food Delivery App</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"> <!-- Add your CSS -->
    <style>
        .order-history-container {
            width: 80%;
            margin: 30px auto;
            padding: 20px;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #fc8019;
            color: white;
        }
        .empty-msg {
            text-align: center;
            color: #555;
            padding: 20px;
        }
        .btn-home {
            display: inline-block;
            padding: 10px 15px;
            margin-top: 20px;
            background: #fc8019;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }
        .btn-home:hover {
            background: #e96e0e;
        }
    </style>
</head>
<body>

<div class="order-history-container">
    <h2>Your Order History</h2>

    <% if (orderHistory == null || orderHistory.isEmpty()) { %>
        <div class="empty-msg">You have not placed any orders yet.</div>
        <a href="home.jsp" class="btn-home">Go to Home</a>
    <% } else { %>
        <table>
            <tr>
                <th>Order ID</th>
                <th>Restaurant ID</th>
                <th>Order Date</th>
                <th>Payment Method</th>
                <th>Status</th>
                <th>Total Amount (₹)</th>
            </tr>
            <% for (Order order : orderHistory) { %>
                <tr>
                    <td><%= order.getOrderId() %></td>
                    <td><%= order.getRestaurantId() %></td>
                    <td><%= order.getOrderDate() %></td>
                    <td><%= order.getPaymentMethod() %></td>
                    <td><%= order.getStatus() %></td>
                    <td>₹<%= String.format("%.2f", order.getTotalAmount()) %></td>
                </tr>
            <% } %>
        </table>
    <% } %>
</div>

</body>
</html>
