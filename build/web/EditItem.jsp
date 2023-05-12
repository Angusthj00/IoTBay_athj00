<%-- 
    Document   : EditItem
    Created on : 11 May 2023, 3:32:01 am
    Author     : angus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Item"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="StyleSheet" href="style/ItemManagement.css" type="text/css">
        <link rel="StyleSheet" href="style/Navbar.css" type="text/css">
        <link rel="StyleSheet" href="style/AddItem.css" type="text/css">        
        <title>Edit Item</title>
    </head> 
    <body>
        <!--navbar here-->
        <%@include file="Navbar.jsp"%>
        
        <div class="content">
            <!--pop up message here-->
            <%
                String addItemErr = (String) session.getAttribute("addItemErr");
            %>
            
            <%
                if (addItemErr != null) {
            %>
            <div id="popupContainer" class="pop-up-message-container slide-in">
                <div class="indicator"></div>
                <div class="indicator-icon">
                    <img src="images/x-mark.png" alt="indicator-icon"/>
                </div>
                <div class="pop-up-content">
                    <div class="pop-up-title">Error</div>
                    <div class="pop-up-text"></div>
                </div>
            </div>
            <script>
                var popupDiv = document.getElementById("popupContainer");           
                setTimeout(function() {
                    popupDiv.classList.add("slide-out");  
                }, 4000);
            </script>
            <%  
                session.removeAttribute("addItemErr");
                } 
            %>            

            <div class="page-nav">
                <div class="back" onclick="window.location.href='ItemManagement.jsp'">
                    <img src="images/previous.png" alt="back"/>
                    Back
                </div>
                <div class="page-nav-title">
                    Edit Item
                </div>
            </div>
            
            <%
                ArrayList<ArrayList<String>> categoryList = (ArrayList<ArrayList<String>>) session.getAttribute("categories");

                String itemName = request.getParameter("name");
                String itemCategory = request.getParameter("category");
                String itemImane = request.getParameter("image");
                String itemDescription = request.getParameter("description");
                String itemPrice = request.getParameter("price");
                String itemQuantity = request.getParameter("quantity");
            %>
            
            <div class="main-content">
                <div class="form-container">
                    <form action="EditItemController?name=<%=itemName%>" method="POST">
<!--                        <div>
                            <label for="category"><span class="asterik">*</span>Category: </label>
                            <input type="text" id="category" name="itemCategory">
                        </div>-->
                        <div>
                            <label for="category"><span class="asterik">*</span>Category: </label>
                            <select name="itemCategory" id="category">
                                <%
                                    for (ArrayList<String> category : categoryList) {
                                    if (!category.get(0).equals("All")) {
                                    String categoryName = category.get(0);
                                %>
                                <option value="<%=categoryName%>" <%=(categoryName.equals(itemCategory)) ? "selected" : ""%>><%=categoryName%></option>
                                <% }} %>
                                
                                <option value="new category">---New Category---</option>
                            </select>
                        </div>
                                
                        <div>
                            <label for="itemName"><span class="asterik">*</span>Item Name: </label>
                            <input type="text" id="itemName" name="itemName" placeholder="Item name. Max 100 characters" value="<%=itemName%>">
                        </div>
                                
                        <div class="upload-image-container">
                            <label for="image"><span class="asterik">*</span>Image: </label>
                            <input type="file" id="image" name="itemImage" accept="image/*" onchange="loadFile(event)">

                            <div class="image-container">
                                <img id="image-output"/>
                                
                                <div class="image-upload-div">
                                    <img src="images/upload.png" alt="upload">
                                    <div>
                                        Click/drag a picture for uploading
                                    </div>
                                </div>
                            </div>
                        </div>
                                
                        <script>
                            var loadFile = function(event) {
                                var output = document.getElementById("image-output");
                                output.src = URL.createObjectURL(event.target.files[0]);
                                output.onload = function () {
                                    URL.revokeObjectURL(output.src); //free memory
                                };
                            };
                        </script>
                                
                        <div class="description-container">
                            <label for="description">Description: </label>
                            <textarea id="description" name="itemDescription" maxlength="500"><%=itemDescription%></textarea>
                        </div>
                                
                        <div>
                            <label for="price"><span class="asterik">*</span>Price: </label>
                            <input type="text" id="price" name="itemPrice" placeholder="Enter price" value="<%=itemPrice%>">
                        </div>
                                
                        <div>
                            <label for="quantity"><span class="asterik">*</span>Quantity: </label>
                            <input type="text" id="quantity" name="itemQuantity" placeholder="Enter in stock quantity" value="<%=itemQuantity%>">
                        </div>
                        
                        <div class="separator"></div>
                        
                        <div class="save-container">
                            <input class="add-product-button" type="submit" value="Update & return">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

