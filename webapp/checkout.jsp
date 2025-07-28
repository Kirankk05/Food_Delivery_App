<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.foodDelivery.model.User" %>
<%
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout - Food Delivery</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #fff8f0;
            margin: 0;
            padding: 20px;
        }
        .checkout-container {
            max-width: 500px;
            margin: 0 auto;
            background-color: white;
            padding: 40px 30px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
        }
        h2 {
            text-align: center;
            color: #fc8019;
            margin-bottom: 30px;
        }
        label {
            display: block;
            margin-top: 20px;
            font-weight: 600;
            color: #333;
        }
        input[type="text"], select {
            width: 100%;
            padding: 12px;
            margin-top: 8px;
            border-radius: 6px;
            border: 1px solid #ccc;
            font-size: 15px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            margin-top: 30px;
            width: 100%;
            padding: 12px;
            background-color: #fc8019;
            color: white;
            border: none;
            border-radius: 6px;
            font-weight: bold;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #e86f0f;
        }
    </style>
</head>
<body>

<div class="checkout-container">
    <h2>Confirm Your Order</h2>
    <form action="checkout" method="post">

        <label for="address">Delivery Address:</label>
        <input type="text" name="address" id="address" required placeholder="E.g., #12, 2nd Cross, Indiranagar, Bangalore">

        <label for="paymentMethod">Select Payment Method:</label>
        <select name="paymentMethod" id="paymentMethod" required>
            <option value="">--Choose Payment Method--</option>
            <option value="Cash">Cash on Delivery</option>
            <option value="UPI">UPI</option>
            <option value="Credit Card">Credit Card</option>
        </select>

        <input type="submit" value="Place Order">
    </form>
</div>

</body>
</html>
