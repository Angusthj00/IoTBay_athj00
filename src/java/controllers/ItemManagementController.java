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
public class ItemManagementController extends HttpServlet {
//    private DBConnector db;
//    private DBItemManager itemManager;
//    private Connection conn;
   
//    @Override
//    public void init() {
//        HttpSession session = request.getSession();
//        try {
//            db = new DBConnector();
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(ItemManagementController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        conn = db.openConnection();
//        
//        try {
//            itemManager = new DBItemManager(conn);
//        } catch (SQLException ex) {
//            Logger.getLogger(ItemManagementController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        request.getSession().setAttribute("itemManager", itemManager);
//    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        //get session attribute ssaved by ConnServlet
        DBItemManager itemManager = (DBItemManager) session.getAttribute("itemManager");
        
        //to here
        ArrayList<Item> items = new ArrayList<>();
        String selectedCategory =  request.getParameter("category");        

        //fetch Items based on category selection        
        try {
            ArrayList<ArrayList<String>> categories = itemManager.fetchCategories();
            
            //find items by categories
            if (selectedCategory == null) {
                session.setAttribute("selectedCategory", "All");
                //items = itemManager.fetchItems();
                items = itemManager.fetchItemsByCategory("", "All");

            } else {
                session.setAttribute("selectedCategory", selectedCategory);
                items = itemManager.fetchItemsByCategory("", selectedCategory);
            }
            
            session.removeAttribute("selectedSort");
            session.setAttribute("categories", categories);
            session.setAttribute("items", items);
            
            request.getRequestDispatcher("ItemManagement.jsp").include(request, response); 
            
        } catch (SQLException ex) {
            Logger.getLogger(ItemManagementController.class.getName()).log(Level.SEVERE, null, ex);
//            request.getRequestDispatcher("ItemManagement.jsp").include(request, response); 
        }

    }
//    
//    @Override
//    public void destroy() {
//        try {
//            db.closeConnection();
//        } catch (SQLException ex) {
//            Logger.getLogger(ItemManagementController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
