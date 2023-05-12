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

import model.DAO.*;
import models.Item;


/**
 *
 * @author angus
 */
public class EditItemController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        Validator validator = new Validator();
                
        DBItemManager itemManager = (DBItemManager) session.getAttribute("itemManager");
         
        String itemToEdit = request.getParameter("name");
        String selectedCategory = (String) session.getAttribute("selectedCategory");
        
        String itemName = request.getParameter("itemName");
        String itemCategory = request.getParameter("itemCategory");
        String itemImage = request.getParameter("itemImage");
        String itemDescription = request.getParameter("itemDescription");
        String itemPrice = request.getParameter("itemPrice");
        String itemQuantity = request.getParameter("itemQuantity");
          
        ArrayList<Item> items = new ArrayList<>();
        
        if (validator.checkEmpty(itemName, itemCategory, itemImage, itemPrice, itemQuantity)) {
            session.setAttribute("addItemErr", "Please fill in the required fields.");
            request.getRequestDispatcher("AddItem.jsp").include(request, response);
        } else if (!validator.validateItemName(itemName)) {
            session.setAttribute("addItemErr", "Please enter a valid item name.");
            request.getRequestDispatcher("AddItem.jsp").include(request, response);
        } else if (!validator.validateItemCategory(itemCategory)) {
            session.setAttribute("addItemErr", "Please enter a valid item category.");
            request.getRequestDispatcher("AddItem.jsp").include(request, response); 
        } else if (!validator.validateItemImage(itemImage)) {
            session.setAttribute("addItemErr", "Please upload a valid image format (jpeg/jpg/png).");
            request.getRequestDispatcher("AddItem.jsp").include(request, response);
        } else if (!validator.validateItemDescription(itemDescription)) {
            session.setAttribute("addItemErr", "Please enter a valid item description.");
            request.getRequestDispatcher("AddItem.jsp").include(request, response);
        } else if (!validator.validateItemCost(itemPrice)) {
            session.setAttribute("addItemErr", "Please enter a valid item price.");
            request.getRequestDispatcher("AddItem.jsp").include(request, response);
        } else if (!validator.validateItemQuantity(itemQuantity)) {
            session.setAttribute("addItemErr", "Please enter a valid item quantity.");
            request.getRequestDispatcher("AddItem.jsp").include(request, response);
        } else {
            try {  
                double doublePrice = Double.parseDouble(itemPrice);
                int intQuantity = Integer.parseInt(itemQuantity);
                
                itemManager.updateItem(itemToEdit, itemName, itemCategory, itemImage, itemDescription, doublePrice, intQuantity);

                if (selectedCategory.equals(itemCategory)) { //if user didnt change the category field
                    items = itemManager.fetchItemsByCategory("", selectedCategory);            
                } else { //if user change the category field
                    items = itemManager.fetchItemsByCategory("", itemCategory);            
                    session.setAttribute("selectedCategory", itemCategory);
                }

                session.setAttribute("popupMsg", "Item updated.");
                session.setAttribute("items", items);
                request.getRequestDispatcher("ItemManagement.jsp").include(request, response);

            } catch (SQLException ex) {
                Logger.getLogger(EditItemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
