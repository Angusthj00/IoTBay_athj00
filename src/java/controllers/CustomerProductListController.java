/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.sql.SQLException;
//import java.sql.Connection;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import models.Item;
import model.DAO.*;


/**
 *
 * @author angus
 */
public class CustomerProductListController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        //get session attribute ssaved by ConnServlet
        DBItemManager itemManager = (DBItemManager) session.getAttribute("itemManager");
        
        //to here
        ArrayList<Item> userItems = new ArrayList<>();
        String selectedCategory =  request.getParameter("category");  

        //fetch Items based on category selection        
        try {
            ArrayList<ArrayList<String>> categories = itemManager.fetchCategories();
            
            //find items by categories
            if (selectedCategory == null) {
                session.setAttribute("userSelectedCategory", "All");
                //items = itemManager.fetchItems();
                userItems = itemManager.fetchItemsByCategory("", "All");

            } else {
                session.setAttribute("userSelectedCategory", selectedCategory);
                userItems = itemManager.fetchItemsByCategory("", selectedCategory);
            }
            
            session.removeAttribute("userSelectedSort");
            session.setAttribute("userCategories", categories);
            session.setAttribute("userItems", userItems);
            
            //store image path in session
            String imagePath = "DBImages/";
            session.setAttribute("imagePath", imagePath);
            
            request.getRequestDispatcher("ProductListCustomer.jsp").include(request, response); 
            
        } catch (SQLException ex) {
            Logger.getLogger(CustomerProductListController.class.getName()).log(Level.SEVERE, null, ex);
//            request.getRequestDispatcher("ItemManagement.jsp").include(request, response); 
        }

    }

    
}
