<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="models.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="StyleSheet" href="style/NavbarCustomer.css" type="text/css">
        
    <title>Shipment List</title>
    <style>
        /* CSS styles */
        body {
            font-family: Arial, sans-serif;
            padding: 0;
            margin: 0;
            height: 100vh;
            width: 100%;
        }

        h1 {
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 80px;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        a {
            margin-right: 10px;
        }

        .create-shipment-link {
            display: block;
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <!--navbar here-->
    <%@include file="NavbarCustomer.jsp"%>
    
    <h1>Shipment List</h1>
    <table>
        <tr>
            <th>Shipment ID</th>
            <th>Shipment Method</th>
            <th>Shipment Date</th>
            <th>Shipment Address</th>
            <th>Actions</th>
        </tr>
        <%
            ArrayList<Shipment> shipmentList = (ArrayList<Shipment>) session.getAttribute("shipments");
            for  (Shipment shipment : shipmentList) {
        %>
            <tr>
                <td><%= shipment.getShipmentId()%></td>
                <td><%= shipment.getShipmentMethod()%></td>
                <td><%= shipment.getShipmentDate()%></td>
                <td><%= shipment.getShipmentAddress()%></td>
                <td>
                    <a href="editShipment.jsp?shipmentId=<%= shipment.getShipmentId()%>&shipmentMethod=<%= shipment.getShipmentMethod()%>&shipmentDate=<%= shipment.getShipmentDate()%>&shipmentAddress=<%= shipment.getShipmentAddress()%>">Edit</a>
                    <a href="DeleteShipmentController?shipmentId=<%= shipment.getShipmentId()%>" onclick="return confirm('Are you sure you want to delete this shipment?')">Delete</a>
                </td>
            </tr>
        <%
            }
        %>
    </table>

    <a href="shipment.jsp" class="create-shipment-link">Create Shipment</a>
</body>
</html>
