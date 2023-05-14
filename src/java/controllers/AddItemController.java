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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.InputStream;

import model.DAO.*;
import models.Item;


/**
 *
 * @author angus
 */
@MultipartConfig
public class AddItemController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        Validator validator = new Validator();
        
        //add item form datas
        //*****important**** any data from getParameter input are all string
        //**** so need to server-side validate input                                                                                                               
        String itemName = request.getParameter("itemName");
        String itemCategory;
        Part file = request.getPart("itemImage");
        String itemDescription = request.getParameter("itemDescription");
        String itemCost = request.getParameter("itemPrice");
        String itemQuantity = request.getParameter("itemQuantity");
        
        DBItemManager itemManager = (DBItemManager) session.getAttribute("itemManager");

        if (!request.getParameter("itemCategory").equals("newCategory")) {
            itemCategory = request.getParameter("itemCategory");
        } else {
            itemCategory = request.getParameter("newCategory");
        }
        
        if (validator.checkEmpty(itemName, itemCategory, itemCost, itemQuantity)) {
            session.setAttribute("addItemErr", "Please fill in the required fields.");
            request.getRequestDispatcher("AddItem.jsp").include(request, response);
        } else if (file.getSize() == 0) {
            session.setAttribute("addItemErr", "Please upload an image.");
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
        } else if (!validator.validateItemCost(itemCost)) {
            session.setAttribute("addItemErr", "Please enter a valid item price.");
            request.getRequestDispatcher("AddItem.jsp").include(request, response);
        } else if (!validator.validateItemQuantity(itemQuantity)) {
            session.setAttribute("addItemErr", "Please enter a valid item quantity.");
            request.getRequestDispatcher("AddItem.jsp").include(request, response);
        } else {  
            
            //image upload process
            String imageFileName = file.getSubmittedFileName(); //get selected image file name

            //upload path where we have to upload our actual image
            String uploadPath = "C:/Users/angus/OneDrive/Documents/NetBeansProjects/IoTBayProductCatalogue/web/DBImages/" + imageFileName;
            
            try {
                //image process*****
                FileOutputStream fos = new FileOutputStream(uploadPath);
                InputStream is = file.getInputStream();

                byte[] data = new byte[is.available()];
                is.read(data);
                fos.write(data);
                fos.close(); //*****
                
                double doublePrice = Double.parseDouble(itemCost);
                int intQuantity = Integer.parseInt(itemQuantity);
                
                itemManager.addItem(itemName, itemCategory, imageFileName, itemDescription, doublePrice, intQuantity);
                
                //store image path in session
                String imagePath = "DBImages/";
                session.setAttribute("imagePath", imagePath);
                
                ArrayList<ArrayList<String>> categories = itemManager.fetchCategories();
                session.setAttribute("categories", categories);
                                

                ArrayList<Item> items = itemManager.fetchItemsByCategory("", itemCategory);
                session.setAttribute("selectedCategory", itemCategory);
                session.setAttribute("items", items);
                
                session.setAttribute("popupMsg", "Item added successfully.");
                request.getRequestDispatcher("ItemManagement.jsp").include(request, response);

            } catch (SQLException ex) {
                Logger.getLogger(AddItemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
