<%@ page import="com.foodDelivery.model.User" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Food Delivery App</title>
    <style>
        body {
            margin: 0;
            font-family: 'Inter', sans-serif;
            background-color: #f9f9f9;
        }

        .navbar {
            background-color: #ffffff;
            padding: 16px 40px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar .logo {
            color: #fc8019;
            font-size: 24px;
            font-weight: bold;
            text-decoration: none;
        }

        .navbar .nav-links {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .navbar .nav-links a {
            color: #fc8019;
            text-decoration: none;
            font-weight: bold;
            font-size: 16px;
        }

        .navbar .nav-links a:hover {
            text-decoration: underline;
        }

        .navbar .welcome {
            color: #333;
            font-weight: bold;
            margin-right: 20px;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <a href="home.jsp" class="logo">Food Delivery App</a>
        <div class="nav-links">
            <% if (loggedInUser != null) { %>
                <div class="welcome">Welcome, <%= loggedInUser.getUserName() %></div>
                <a href="home.jsp">Home</a>
                <a href="orderhistory.jsp">Order History</a>
                <a href="logout.jsp">Logout</a>
            <% } else { %>
                <a href="home.jsp">Home</a>
                <a href="restaurant.jsp">Restaurants</a>
            <% } %>
        </div>
    </div>
