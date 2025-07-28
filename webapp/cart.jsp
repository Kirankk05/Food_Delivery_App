<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, javax.servlet.http.HttpSession, com.foodDelivery.model.Cart, com.foodDelivery.model.CartItem" %>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    Object restaurantIdObj = session.getAttribute("restaurantId");
    Object userIdObj = session.getAttribute("userId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
    <link rel="stylesheet" href="css/card-style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fff8f0;
            margin: 0;
            padding: 20px;
        }

        .page-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .cart-container {
            max-width: 800px;
            margin: 0 auto;
        }

        .cart-item {
            background-color: #ffffff;
            padding: 20px;
            margin-bottom: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        .cart-item h3 {
            margin: 0 0 10px 0;
        }

        .cart-item p {
            margin: 5px 0;
        }

        .cart-item form {
            margin-top: 10px;
        }

        input[type="number"] {
            width: 60px;
            padding: 4px;
        }

        .update-btn, .remove-btn {
            background-color: #fc8019;
            color: white;
            border: none;
            padding: 6px 12px;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
            margin-left: 10px;
        }

        .update-btn:hover {
            background-color: #e86f0f;
        }

        .remove-btn {
            background-color: #c0392b;
        }

        .remove-btn:hover {
            background-color: #a93226;
        }

        .add-more-items-btn {
            background-color: #3498db;
            color: white;
            padding: 8px 14px;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
            display: inline-block;
            margin-top: 30px;
            transition: background-color 0.3s;
        }

        .add-more-items-btn:hover {
            background-color: #217dbb;
        }

        .proceed-to-checkout-btn {
            background-color: #2ecc71;
            color: white;
            padding: 8px 14px;
            border: none;
            border-radius: 6px;
            font-weight: bold;
            cursor: pointer;
            margin-left: 20px;
            transition: background-color 0.3s;
        }

        .proceed-to-checkout-btn:hover {
            background-color: #27ae60;
        }

        .actions {
            margin-top: 30px;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="page-header">
    <h1>Your Cart</h1>
</div>

<div class="cart-container">
<%
    if (cart != null && !cart.getItems().isEmpty()) {
        for (CartItem item : cart.getItems().values()) {
%>
        <div class="cart-item">
            <h3><%= item.getName() %></h3>
            <p>Price: ₹<%= item.getPrice() %></p>
            <p>Quantity: <%= item.getQuantity() %></p>

            <form action="cart" method="post">
                <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                <label>
                    Quantity:
                    <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1">
                </label>
                <input type="submit" name="action" value="update" class="update-btn">
                <input type="submit" name="action" value="remove" class="remove-btn">
            </form>
        </div>
<%
        }
    } else {
%>
    <div style="text-align:center; margin-top: 50px;">
        <h2>Your cart is empty.</h2>
        <%
            if (restaurantIdObj != null) {
        %>
            <a href="menu?restaurantId=<%= restaurantIdObj %>" class="add-more-items-btn">
                &#8592; Add Items
            </a>
        <%
            }
        %>
    </div>
<%
    }
%>

<% if (cart != null && !cart.getItems().isEmpty()) { %>
    <div class="actions">
        <% if (restaurantIdObj != null) { %>
            <a href="menu?restaurantId=<%= restaurantIdObj %>" class="add-more-items-btn">
                &#8592; Add More Items
            </a>
        <% } %>

        <form action="checkout.jsp" method="post" style="display:inline;">
    <input type="submit" value="Proceed to Checkout" class="proceed-to-checkout-btn">
</form>

    </div>
<% } %>

</div>

</body>
</html>
