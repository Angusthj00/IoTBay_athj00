<%-- 
    Document   : ProductDetails
    Created on : 16 May 2023, 4:37:24 pm
    Author     : angus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Item"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="StyleSheet" href="style/ProductDetails.css" type="text/css">
        <link rel="StyleSheet" href="style/NavbarCustomer.css" type="text/css">
        <title>Product Details</title>
    </head>
    <body>
        <!--navbar here-->
        <%@include file="NavbarCustomer.jsp"%>
        
        <div class="product-detail-container">
            <%
                String itemName = request.getParameter("name");
                String itemCategory = request.getParameter("category");
                String itemImage = request.getParameter("image");
                String itemDescription = request.getParameter("description");
                String itemPrice= request.getParameter("price");
                String itemQuantity = request.getParameter("quantity");
                
                String imagePath = (String) session.getAttribute("imagePath");
            %>
            <div class="section1">
                <div class="sin-product-image">
                    <img src="<%=imagePath + itemImage%>" alt="<%=itemName%>"/>
                </div>
            </div>
            
            <div class="section2">
                
                <div class="separator"></div>
                <div class="sin-product-name">
                    <%=itemName%>
                </div>

                <div class="sin-product-category">
                    <%=itemCategory%>
                </div>
                <div class="separator"></div>

                <div class="sin-product-price">
                    <span class="currency">A$</span>
                    <span class="dollar"><%=itemPrice.split("\\.")[0]%></span>
                    <span class="cent"><%=itemPrice.split("\\.")[1]%></span>
                </div>

                <div class="sin-product-desc">
                    <%=itemDescription%>
                </div>
                <div class="separator"></div>

            </div>
                
            <div class="section3">
                <div class="sin-product-price">
                    <span class="currency">A$</span>
                    <span class="dollar"><%=itemPrice.split("\\.")[0]%></span>
                    <span class="cent"><%=itemPrice.split("\\.")[1]%></span>
                </div>
                
                <div class="delivery-section">
                    
                </div>
                
                <div class="sin-product-quantity">
                    <!--in stock-->
                    <div class="stock-container">
                        <% if (Integer.parseInt(itemQuantity) <= 3) { %>
                        <div class="low-stock">
                            Only <%=itemQuantity%> left in stock.
                        </div>
                        <% } else { %>
                        <div class="in-stock">
                            In stock
                        </div>
                        <% } %>
                    </div>

                    
                    <!--set quantity (option based on stock)-->
                    <div class="set-quantity">
                        <label for="quantity">Quantity: </label>
                        <select name="quantity" id="quantity">
                            <% for (int i = 1; i <= Integer.parseInt(itemQuantity); i++) { %>
                            <option value="<%=i%>"><%=i%></option>
                            <% } %>
                        </select>
                    </div>
                </div>
                
                <div class="add-to-cart-button">
                    Add to Cart
                </div>
            </div>
            

        </div>
        
    </body>
</html>
