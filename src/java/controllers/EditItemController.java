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

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 *
 * @author angus
 */

@MultipartConfig
public class EditItemController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        Validator validator = new Validator();
                
        DBItemManager itemManager = (DBItemManager) session.getAttribute("itemManager");
         
        String itemToEdit = request.getParameter("name");
        
        String itemName = request.getParameter("itemName");
        String itemCategory = request.getParameter("itemCategory");
        String itemImage = request.getParameter("image");
        Part file = request.getPart("itemImage");
        String itemDescription = request.getParameter("itemDescription");
        String itemPrice = request.getParameter("itemPrice");
        String itemQuantity = request.getParameter("itemQuantity");
                  
        if (validator.checkEmpty(itemName, itemCategory, itemPrice, itemQuantity)) {
            session.setAttribute("addItemErr", "Please fill in the required fields.");
            request.getRequestDispatcher("AddItem.jsp").include(request, response);
        } else if (!validator.validateItemName(itemName)) {
            session.setAttribute("addItemErr", "Please enter a valid item name.");
            request.getRequestDispatcher("AddItem.jsp").include(request, response);
        } else if (!validator.validateItemCategory(itemCategory)) {
            session.setAttribute("addItemErr", "Please enter a valid item category.");
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
                //image upload process

//                if (file.getSize() != 0) { //if upload a new image
            String imageFileName = file.getSubmittedFileName(); //get selected image file name

                    //upload path where we have to upload our actual image
            String uploadPath = "C:/Users/angus/OneDrive/Documents/NetBeansProjects/IoTBayProductCatalogue/web/DBImages/" + imageFileName;
                    

                
//                } else {
//                    imageFileName = itemImage;
//                }            
            try {  
                //image process*****
                FileOutputStream fos = new FileOutputStream(uploadPath);
                InputStream is = file.getInputStream();

                byte[] data = new byte[is.available()];
                is.read(data);
                fos.write(data);
                fos.close(); //*****

                double doublePrice = Double.parseDouble(itemPrice);
                int intQuantity = Integer.parseInt(itemQuantity);

                itemManager.updateItem(itemToEdit, itemName, itemCategory, "", itemDescription, doublePrice, intQuantity);
                ArrayList<Item> items = itemManager.fetchItemsByCategory(itemName, itemCategory);
                session.setAttribute("items", items);
                
                session.setAttribute("popupMsg", "Item updated.");
                request.getRequestDispatcher("ItemManagement.jsp").include(request, response);

            } catch (SQLException ex) {
                Logger.getLogger(EditItemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}            