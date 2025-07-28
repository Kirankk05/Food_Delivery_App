<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.foodDelivery.model.Restaurant, com.foodDelivery.dao.RestaurantDAO , com.foodDelivery.daoImp.RestaurantDAOImp, com.foodDelivery.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - Explore Restaurants</title>
    <link rel="stylesheet" href="css/home.css">
    <style>
        body {
            margin: 0;
            font-family: 'Inter', sans-serif;
            background-color: #f9f9f9;
        }

        .header {
            background-color: #fff;
            padding: 16px 40px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header h1 {
            color: #fc8019;
            font-size: 24px;
        }

        .nav-links a {
            text-decoration: none;
            color: #fc8019;
            font-weight: bold;
            margin-right: 20px;
        }

        .restaurant-container {
            padding: 40px;
            max-width: 1200px;
            margin: auto;
        }

        .restaurant-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 24px;
        }

        .restaurant-card {
            background-color: #fff;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.06);
            overflow: hidden;
            transition: transform 0.2s ease;
        }

        .restaurant-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 24px rgba(0,0,0,0.1);
        }

        .restaurant-image {
            width: 100%;
            height: 180px;
            object-fit: cover;
        }

        .restaurant-content {
            padding: 16px;
        }

        .restaurant-name {
            font-size: 18px;
            font-weight: 600;
            color: #282c3f;
            margin-bottom: 6px;
        }

        .restaurant-details {
            font-size: 14px;
            color: #686b78;
            margin-bottom: 4px;
        }

        .no-data {
            text-align: center;
            font-size: 18px;
            margin-top: 60px;
            color: #999;
        }

        .button {
            display: inline-block;
            padding: 8px 16px;
            background-color: #fc8019;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }
        .button:hover {
            background-color: #e86f0f;
        }
        
        .nav-item {
             color: #fc8019;
             font-weight: bold;
             margin-right: 20px;
             text-decoration: none;
}
        
    </style>
</head>
<body>

<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>

<div class="header">
    <h1>Food Delivery App</h1>
    <div class="nav-links">
        <a href="home.jsp">Home</a>
        <% if (loggedInUser != null) { %>
    <a href="viewOrderHistory.jsp">View Order History</a>
    <span class="nav-item">Welcome, <b><%= loggedInUser.getUserName() %></b></span>
    &nbsp;|&nbsp;
    <a href="logout.jsp">Logout</a>
<% } else { %>
    <a href="login.jsp">Login</a>
    <a href="register.jsp">Sign Up</a>
<% } %>

    </div>
</div>

<%
List<Restaurant> restaurants = (List<Restaurant>) session.getAttribute("restaurants");

if (restaurants == null) {
    // Load from DB
    RestaurantDAO restaurantDAO = new RestaurantDAOImp();
    restaurants = restaurantDAO.getAllRestaurants(); 
    session.setAttribute("restaurants", restaurants);
}
%>
<% 
    if (restaurants != null && !restaurants.isEmpty()) {
%>
    <div class="restaurant-container">
        <div class="restaurant-grid">
<%
            for (Restaurant restaurant : restaurants) {
                String imgPath = (restaurant.getImagePath() != null && !restaurant.getImagePath().trim().isEmpty())
                                 ? restaurant.getImagePath()
                                 : "/images/default.jpg";
%>
                <div class="restaurant-card" id="restaurants">
                    <img class="restaurant-image" src="<%= imgPath %>" alt="Restaurant Image">
                    <div class="restaurant-content">
                        <div class="restaurant-name"><%= restaurant.getName() %></div>
                        <div class="restaurant-details">&#11088; <%= restaurant.getRating() %> &#8226; <%= restaurant.getDeliveryTime() %> mins</div>
                        <div class="restaurant-details"><%= restaurant.getCuisineType() %></div>
                        <div class="restaurant-details"><%= restaurant.getAddress() %></div>
                        <a href="menu?restaurantId=<%= restaurant.getRestaurantId() %>" class="button">View Menu</a>
                    </div>
                </div>
<%
            }
%>
        </div>
    </div>
<%
    } else {
%>
    <div class="no-data">No restaurants available.</div>
<%
    }
%>

</body>
</html>
