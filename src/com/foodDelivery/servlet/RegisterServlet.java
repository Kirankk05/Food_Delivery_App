package com.foodDelivery.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.foodDelivery.dao.UserDAO;
import com.foodDelivery.daoImp.UserDAOImp;
import com.foodDelivery.model.User;
import com.foodDelivery.util.UserIDGenerator;
import com.foodDelivery.util.PasswordHashing;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAOImp();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null) {
            response.sendRedirect("home.jsp");
            return;
        }

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String rawPassword = request.getParameter("password");

        // Hash the password before saving
        String hashedPassword = PasswordHashing.hashPassword(rawPassword);

        String address = phone;
        String role = "customer";
        int id = UserIDGenerator.generateUserId();

        User user = new User(id, username, hashedPassword, email, address, role);
        userDAO.addUser(user);

        response.sendRedirect("login.jsp");
    }
}
