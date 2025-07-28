package com.foodDelivery.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodDelivery.daoImp.MenuDAOImp;
import com.foodDelivery.model.Menu;
@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
	private MenuDAOImp menuDAO;
	
	@Override
	public void init() throws ServletException {
		this.menuDAO = new MenuDAOImp();
	}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String restaurantId = req.getParameter("restaurantId");
        if (restaurantId != null) {
            try {
                int id = Integer.parseInt(restaurantId);
                
                req.getSession().setAttribute("restaurantId", id);
                
               
                List<Menu> menuList = menuDAO.getAllMenuByRestaurant(id);
                req.setAttribute("menuList", menuList);
                req.getRequestDispatcher("menu.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid restaurant ID");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing restaurant ID");
        }
    }
}

