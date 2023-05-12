/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.DBItemManager;
import models.Item;

/**
 *
 * @author angus
 */
public class SearchItemController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        //get session attribute ssaved by ConnServlet
        DBItemManager itemManager = (DBItemManager) session.getAttribute("itemManager");
        
        //fetch Items based on search input 
        ArrayList<Item> items = new ArrayList<>();
        String selectedCategory = (String) session.getAttribute("selectedCategory");
        
        try {
            String searchInput = request.getParameter("searchInput");
                            
            items = itemManager.fetchItemsByCategory(searchInput, selectedCategory);            
            
            if (items.isEmpty()) {
                session.setAttribute("noItemErr", "No item named '" + searchInput + "' was found in '" + selectedCategory + "'.");
//                request.getRequestDispatcher("ItemManagement.jsp").include(request, response); 
            }
            
            session.removeAttribute("selectedSort");
            session.setAttribute("items", items); 
            request.getRequestDispatcher("ItemManagement.jsp").include(request, response); 
            
        } catch (SQLException ex) {
            Logger.getLogger(SearchItemController.class.getName()).log(Level.SEVERE, null, ex);
            //request.getRequestDispatcher("ItemManagement.jsp").include(request, response); 
        }
    }


}
