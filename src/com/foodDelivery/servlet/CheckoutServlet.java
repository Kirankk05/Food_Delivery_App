package com.foodDelivery.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodDelivery.dao.OrderDAO;
import com.foodDelivery.daoImp.OrderDAOImp;
import com.foodDelivery.model.Cart;
import com.foodDelivery.model.CartItem;
import com.foodDelivery.model.Order;
import com.foodDelivery.model.OrderItem;
import com.foodDelivery.model.User;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() {
        this.orderDAO = new OrderDAOImp();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("loggedInUser");

        // If user not logged in, redirect to login page
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // If cart is empty, redirect back to cart
        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        String paymentMethod = request.getParameter("paymentMethod");

        Order order = new Order();
        order.setUserId(user.getUserId());

        Object restIdObj = session.getAttribute("restaurantId");
        if (restIdObj instanceof Integer) {
            order.setRestaurantId((int) restIdObj);
        } else if (restIdObj instanceof String) {
            order.setRestaurantId(Integer.parseInt((String) restIdObj));
        }

        order.setOrderDate(new Date());
        order.setPaymentMethod(paymentMethod);
        order.setStatus("Pending");

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (CartItem item : cart.getItems().values()) {
            OrderItem oi = new OrderItem();
            oi.setMenuId(item.getItemId());
            oi.setQuantity(item.getQuantity());
            double itemTotal = item.getPrice() * item.getQuantity();
            oi.setItemTotal(itemTotal);
            totalAmount += itemTotal;
            orderItems.add(oi);
        }

        order.setTotalAmount(totalAmount);

        // Save order
        int orderId = orderDAO.addOrder(order);
        orderDAO.addOrderItems(orderId, orderItems);

        // Store for confirmation page
        session.removeAttribute("cart");
        order.setOrderId(orderId);
        session.setAttribute("order", order);
        session.setAttribute("orderItems", orderItems);

        response.sendRedirect("order_confirmation.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            // If user is not logged in, redirect to login
            response.sendRedirect("login.jsp");
        } else {
            // If user is logged in, directly go to checkout page
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        }
    }
}
