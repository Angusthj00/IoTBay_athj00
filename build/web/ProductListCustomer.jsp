<%-- 
    Document   : ProductListCustomer
    Created on : 15 May 2023, 4:48:11 am
    Author     : angus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Item"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="StyleSheet" href="style/ProductListCustomer.css" type="text/css">
        <link rel="StyleSheet" href="style/NavbarCustomer.css" type="text/css">
        <title>Item Management</title>
    </head> 
    <body>
        <!--navbar here-->
        <%@include file="NavbarCustomer.jsp"%>
        
        <div class="content">

            
            <div class="product-list-container">
                
                <%
                    ArrayList<Item> itemList = (ArrayList<Item>) session.getAttribute("userItems");
                    if (itemList.isEmpty()) {
                        String noItemErr = (String) session.getAttribute("noItemErr");
                %>
                <div><%=noItemErr%></div>
                
                <%
                    } else {
                    for (Item item : itemList) {
                    String imagePath = (String) session.getAttribute("imagePath"); //retrieve the image path
                %>
                <div class="product-item-container" onclick="window.location.href='ProductDetails.jsp?name=<%=item.getName()%>&category=<%=item.getCategory()%>&image=<%=item.getImage()%>&description=<%=item.getDescription()%>&price=<%=item.getPrice()%>&quantity=<%=item.getQuantity()%>'">
                    <div class="product-image-container">
                        <img src="<%=imagePath + item.getImage()%>" alt="product image"/>
                    </div>
                    <div class="product-info">
                        <div class="product-name">
                            <%=item.getName()%>
                        </div>
                        
                        <div class="product-price">
                            <span class="currency">A$</span>
                            <%
                                double price = item.getPrice();
                                String priceStr = String.format("%.2f", price);
                            %>
                            <span class="dollar"><%=priceStr.split("\\.")[0]%></span>
                            <span class="cent"><%=priceStr.split("\\.")[1]%></span>
                        </div>
                        
                        <%
                            int stock = item.getQuantity();
                            if (stock <= 3) {
                        %>
                        <div class="product-stock">
                            Only <%=stock%> left in stock.
                        </div>
                        <% } %>
                        
                    </div>
                </div>
                <% }} %>
            </div>     
        </div>
            

    </body>
</html>

