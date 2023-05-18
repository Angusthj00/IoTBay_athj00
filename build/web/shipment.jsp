<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shipment Form</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f2f2f2;
        }

        h1 {
            text-align: center;
            margin-top: 50px;
        }

        form {
            max-width: 500px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            border-radius: 5px;
        }

        label {
            display: block;
            margin-bottom: 10px;
        }

        input[type="text"], textarea {
            display: block;
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        input[type="date"] {
            display: block;
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
        }

        input[type="submit"] {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #008CBA;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0077B5;
        }
        
        button {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #008CBA;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #bbb;
        }

    </style>
</head>
<body>
    
    <%
        String err = (String) session.getAttribute("addShipmentError");
        if (err != null) {
    %>
    <p><%=err%></p>
    <% } %>
   
<h1>Shipment Form</h1>
<form action="AddShipmentController" method="post">
<label for="shipmentID">Shipment ID: </label>
<input type="int" name="shipmentID" id="shipmentID" ><br>
<label for="shipmentMethod">Shipment Method:</label>
<!--<input type="radio" name="shipmentMethod" value="DHL Express (3-5 business days)" >DHL Express (3-5 business days)<br>
<input type="radio" name="shipmentMethod" value="International packet（40-60 days, NO guarantee) First 100 Grams $6.00， Additional 100 Grams $1.50" >International packet（40-60 days, NO guarantee) First 100 Grams $6.00， Additional 100 Grams $1.50<br>
<input type="radio" name="shipmentMethod" value="EMS （30-40 days,NO guarantee） First 500 Grams $20.00， Additional 500 Grams $5.00" >EMS （30-40 days,NO guarantee） First 500 Grams $20.00， Additional 500 Grams $5.00<br>-->
<%
    // List of radio input values
    String[] radioValues = {
        "Standard",
        "Express",
        "Pick-Up"
    };

    // Loop through the radio input values
    for (String value : radioValues) {
%>
    <input type="radio" name="shipmentMethod" value="<%= value %>"><%= value %><br>
<%
    }
%>

<br/>
<label for="shipmentDate">Shipment Date:</label>
<input type="text" name="shipmentDate" id="shipmentDate" ><br>
<label for="shipmentAddress">Shipment Address:</label>
<textarea name="shipmentAddress" id="shipmentAddress" ></textarea><br>
<input type="hidden" name="orderId" value="<%=request.getParameter("orderId")%>">
<input type="hidden" name="shipmentId" value="<%=request.getParameter("shipmentId")%>">
<a href="shipmentlist.jsp">Back</a>
<input type="submit" value="Save">
</form>
</body>
</html>
