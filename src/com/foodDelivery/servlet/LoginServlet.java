package com.foodDelivery.servlet;

import com.foodDelivery.dao.UserDAO;
import com.foodDelivery.daoImp.UserDAOImp;
import com.foodDelivery.model.User;
import com.foodDelivery.model.Cart;
import com.foodDelivery.util.PasswordHashing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAOImp();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String rawPassword = request.getParameter("password");

        User user = userDAO.getUserByEmail(email);

        if (user != null) {
            String hashedInputPassword = PasswordHashing.hashPassword(rawPassword);

            if (hashedInputPassword.equals(user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedInUser", user);

                Cart cart = (Cart) session.getAttribute("cart");

                if (cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) {
                    response.sendRedirect("checkout");
                } else {
                    response.sendRedirect("home.jsp");
                }
                return;
            }
        }

        request.setAttribute("errorMessage", "Invalid email or password");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
