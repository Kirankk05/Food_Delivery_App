<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foodDelivery.model.Menu" %>
<!DOCTYPE html>
<html>
<head>
    <title>Menu</title>
    <link rel="stylesheet" href="css/card-style.css">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #fff4e6, #ffe9d6);
        }

        .page-header {
            
            text-align: center;
            padding: 40px 20px;
            background-color: #fc8019;
            color: white;
            font-size: 32px;
            font-weight: bold;
            letter-spacing: 1px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }

        .card-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 30px;
            padding: 40px;
        }

        .card {
            background-color: #fff;
            border-radius: 15px;
            box-shadow: 0 6px 18px rgba(0,0,0,0.1);
            padding: 25px;
            transition: all 0.3s ease;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 22px rgba(0,0,0,0.15);
        }

        .card-info h3 {
            margin: 0;
            font-size: 22px;
            color: #333;
        }

        .card-info p {
            font-size: 14px;
            color: #666;
            margin: 6px 0;
        }

        .availability {
            font-weight: bold;
        }

        .availability.available {
            color: #28a745;
        }

        .availability.unavailable {
            color: #dc3545;
        }

        .quantity-input {
            width: 60px;
            padding: 6px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 6px;
            margin-left: 10px;
        }

        .add-to-cart-btn {
            background-color: #fc8019;
            border: none;
            color: white;
            padding: 8px 16px;
            border-radius: 6px;
            font-weight: bold;
            font-size: 14px;
            cursor: pointer;
            margin-left: 12px;
            transition: background-color 0.3s ease;
        }

        .add-to-cart-btn:hover {
            background-color: #e66e0b;
        }

        .add-to-cart-btn:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }

        form {
            display: flex;
            align-items: center;
            justify-content: flex-start;
            margin-top: 15px;
        }

        .no-items {
            text-align: center;
            margin-top: 80px;
            color: #333;
            font-size: 20px;
        }
    </style>
</head>
<body>

<div class="page-header">
    Explore Our Menu
</div>

<%
    List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");
    if (menuList != null && !menuList.isEmpty()) {
%>
<div class="card-grid">
<%
    for (Menu menu : menuList) {
        boolean available = menu.isAvailabel();
%>
    <div class="card">
        <div class="card-info">
            <h3><%= menu.getItemName() %></h3>
            <p><%= menu.getDescription() %></p>
            <p><strong>Price:</strong> ₹<%= menu.getPrice() %></p>
            <p class="availability <%= available ? "available" : "unavailable" %>">
                <%= available ? "Available" : "Not Available" %>
            </p>

            <form action="cart" method="post">
                <label for="qty-<%= menu.getMenuId() %>">Qty:</label>
                <input
                    type="number"
                    id="qty-<%= menu.getMenuId() %>"
                    name="quantity"
                    value="1"
                    min="1"
                    class="quantity-input"
                    <%= available ? "" : "disabled" %> />

                <input type="hidden" name="itemId" value="<%= menu.getMenuId() %>">
                <input type="hidden" name="action" value="add">
                <button type="submit" class="add-to-cart-btn" <%= available ? "" : "disabled" %>>Add</button>
            </form>
        </div>
    </div>
<%
    }
%>
</div>
<%
    } else {
%>
    <div class="no-items">
        <h2>No menu items available for this restaurant.</h2>
    </div>
<%
    }
%>

</body>
</html>
