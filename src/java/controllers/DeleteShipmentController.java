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
import java.util.ArrayList;
import model.DAO.DBShipmentManager;
import models.Shipment;

/**
 *
 * @author hope9
 */
//@WebServlet(name = "ShipmentManagement", urlPatterns = {"/ShipmentManagement"})
public class DeleteShipmentController extends HttpServlet {
    
    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        DBShipmentManager shipmentManager = (DBShipmentManager) session.getAttribute("shipmentManager");
        
        int idToDelete = Integer.parseInt(request.getParameter("shipmentId"));
        
        try {
            shipmentManager.deleteShipment(idToDelete);
            
            ArrayList<Shipment> shipments = shipmentManager.fetchShipment();
            session.setAttribute("shipments", shipments);
            
            request.getRequestDispatcher("shipmentlist.jsp").include(request, response);
        }
        catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ConnServlet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

}
