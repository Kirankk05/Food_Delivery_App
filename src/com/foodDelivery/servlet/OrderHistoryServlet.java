package com.foodDelivery.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodDelivery.dao.OrderDAO;
import com.foodDelivery.daoImp.OrderDAOImp;
import com.foodDelivery.model.Order;
import com.foodDelivery.model.User;

@WebServlet("/viewOrderHistory")
public class OrderHistoryServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAOImp();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        List<Order> orders = orderDAO.getAllOrdersByUser(user.getUserId());
        req.setAttribute("orderHistory", orders);
        req.getRequestDispatcher("vieworderhistory.jsp").forward(req, resp);
    }
}
