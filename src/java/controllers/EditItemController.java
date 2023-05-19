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
         
        String oriName = request.getParameter("name");
//        String oriCategory = request.getParameter("category");
        String oriImage = request.getParameter("image");
//        String oriDescription = request.getParameter("description");
//        String oriPrice = request.getParameter("price");
//        String oriQuantity = request.getParameter("quantity");
        
        String newName = request.getParameter("itemName");
        String newCategory = request.getParameter("itemCategory");
        Part file = request.getPart("itemImage");
        String newDescription = request.getParameter("itemDescription");
        String newPrice = request.getParameter("itemPrice");
        String newQuantity = request.getParameter("itemQuantity");
                  
        if (validator.checkEmpty(newName, newCategory, newPrice, newQuantity)) {
            session.setAttribute("addItemErr", "Please fill in the required fields.");
            request.getRequestDispatcher("EditItem.jsp").include(request, response);
        } else if (!validator.validateItemName(newName)) {
            session.setAttribute("addItemErr", "Please enter a valid item name.");
            request.getRequestDispatcher("EditItem.jsp").include(request, response);
        } else if (!validator.validateItemCategory(newCategory)) {
            session.setAttribute("addItemErr", "Please enter a valid item category.");
            request.getRequestDispatcher("EditItem.jsp").include(request, response); 
        } else if (!validator.validateItemDescription(newDescription)) {
            session.setAttribute("addItemErr", "Please enter a valid item description.");
            request.getRequestDispatcher("EditItem.jsp").include(request, response);
        } else if (!validator.validateItemCost(newPrice)) {
            session.setAttribute("addItemErr", "Please enter a valid item price.");
            request.getRequestDispatcher("EditItem.jsp").include(request, response);
        } else if (!validator.validateItemQuantity(newQuantity)) {
            session.setAttribute("addItemErr", "Please enter a valid item quantity.");
            request.getRequestDispatcher("EditItem.jsp").include(request, response);
        } else {

            String newImageFileName = file.getSubmittedFileName(); //get selected image file name

            //upload path where we have to upload our actual image
            String uploadPath = "C:/Users/angus/OneDrive/Documents/NetBeansProjects/IoTBayProductCatalogue/web/DBImages/" + newImageFileName;
         
            try {  
                double doublePrice = Double.parseDouble(newPrice);
                int intQuantity = Integer.parseInt(newQuantity);                
                
                if (file.getSize() == 0) { //user did not change the image
                    itemManager.updateItem(oriName, newName, newCategory, oriImage, newDescription, doublePrice, intQuantity);
                } else {
                    //image process*****
                    FileOutputStream fos = new FileOutputStream(uploadPath);
                    InputStream is = file.getInputStream();

                    byte[] data = new byte[is.available()];
                    is.read(data);
                    fos.write(data);
                    fos.close(); //*****
                    itemManager.updateItem(oriName, newName, newCategory, newImageFileName, newDescription, doublePrice, intQuantity);
                }

                ArrayList<Item> items = itemManager.fetchItemsByCategory("", newCategory);
                session.setAttribute("items", items);
                session.setAttribute("selectedCategory", newCategory);
                
                session.setAttribute("popupMsg", "Item updated.");
                request.getRequestDispatcher("ItemManagement.jsp").include(request, response);

            } catch (SQLException ex) {
                Logger.getLogger(EditItemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}            