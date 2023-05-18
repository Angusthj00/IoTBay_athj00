/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.DBShipmentManager;
import models.Shipment;

/**
 *
 * @author hope9
 */
//@WebServlet(name = "ShipmentManagement", urlPatterns = {"/ShipmentManagement"})
public class AddShipmentController extends HttpServlet {
    
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Validator validator = new Validator();
        
        String id = request.getParameter("shipmentID");
        String method = request.getParameter("shipmentMethod");
        String date = request.getParameter("shipmentDate");
        String address = request.getParameter("shipmentAddress");
        
        DBShipmentManager shipmentManager = (DBShipmentManager) session.getAttribute("shipmentManager");

        
        if (validator.checkShipmentEmpty(id, method, date, address)) {
            session.setAttribute("addShipmentError", "Please enter all the required field!!!");
            request.getRequestDispatcher("shipment.jsp").include(request, response);
        } else if (!validator.validateShipmentAddress(address)){
            session.setAttribute("addShipmentError", "Please enter the address based on this format yyyy-mm-dd");
            request.getRequestDispatcher("shipment.jsp").include(request, response);
        } 
        else {
            try {
               shipmentManager.addShipment(Integer.parseInt(id), method, LocalDate.parse(date), address);
               ArrayList<Shipment> shipments = shipmentManager.fetchShipment();
               session.setAttribute("shipments", shipments);
            } catch (SQLException ex) {
                Logger.getLogger(AddShipmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.getRequestDispatcher("shipmentlist.jsp").include(request, response);
            
        }
    }

}
