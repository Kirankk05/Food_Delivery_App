package com.foodDelivery.servlet;

import com.foodDelivery.dao.MenuDAO;
import com.foodDelivery.daoImp.MenuDAOImp;
import com.foodDelivery.model.Cart;
import com.foodDelivery.model.CartItem;
import com.foodDelivery.model.Menu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        String itemIdParam = request.getParameter("itemId");
        String quantityParam = request.getParameter("quantity");

        // Validate action and itemId
        if (action == null || itemIdParam == null || itemIdParam.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        int itemId;
        try {
            itemId = Integer.parseInt(itemIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("cart.jsp");
            return;
        }

        switch (action.toLowerCase()) {
            case "add":
                if (quantityParam != null && !quantityParam.isEmpty()) {
                    try {
                        int quantity = Integer.parseInt(quantityParam);
                        if (quantity > 0) {
                            addItemToCart(request, cart, itemId, quantity);
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
                break;

            case "update":
                if (quantityParam != null && !quantityParam.isEmpty()) {
                    try {
                        int quantity = Integer.parseInt(quantityParam);
                        if (quantity > 0) {
                            cart.updateItem(itemId, quantity);
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
                break;

            case "remove":
                cart.removeItem(itemId);
                break;

            default:
                // Invalid action -> ignore
                break;
        }

        // Redirect to avoid duplicate actions on refresh
        response.sendRedirect("cart.jsp");
    }

    private void addItemToCart(HttpServletRequest request, Cart cart, int itemId, int quantity) {
        MenuDAO menuDAO = new MenuDAOImp();
        Menu menuItem = menuDAO.getMenu(itemId);

        if (menuItem != null) {
            CartItem item = new CartItem(
                    menuItem.getMenuId(),
                    menuItem.getRestaurantId(),
                    menuItem.getItemName(),
                    quantity,
                    menuItem.getPrice()
            );
            cart.addItem(item);

            // Store restaurantId in session (for "Add More Items" link)
            HttpSession session = request.getSession();
            session.setAttribute("restaurantId", menuItem.getRestaurantId());
        }
    }
}
