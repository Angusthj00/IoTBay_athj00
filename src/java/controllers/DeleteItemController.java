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
public class DeleteItemController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        //get session attribute ssaved by ConnServlet
        DBItemManager itemManager = (DBItemManager) session.getAttribute("itemManager");
        
        //fetch Items based on search input 
//        ArrayList<Item> items = new ArrayList<>();
        String itemToDelete = request.getParameter("itemName");
        String selectedCategory = (String) session.getAttribute("selectedCategory");
        try {
            ArrayList<Item> items; 
            
            itemManager.deleteItem(itemToDelete);

            if ((itemManager.fetchItemsByCategory("", selectedCategory)).isEmpty()) { //no more items in that category
                ArrayList<ArrayList<String>> categories = itemManager.fetchCategories(); //update the categories
                session.setAttribute("categories", categories);
                
                session.setAttribute("selectedCategory", "All");
                items = itemManager.fetchItemsByCategory("", "All"); //show All instead

            } else { //still some item in the selected category
                items = itemManager.fetchItemsByCategory("", selectedCategory);
            }
            
            session.setAttribute("popupMsg", "One item deleted.");
            session.setAttribute("items", items);
            
            request.getRequestDispatcher("ItemManagement.jsp").include(request, response);  

        } catch (SQLException ex) {
            Logger.getLogger(DeleteItemController.class.getName()).log(Level.SEVERE, null, ex);
            //request.getRequestDispatcher("ItemManagement.jsp").include(request, response); 
        }
    }


}
