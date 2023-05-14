<%-- 
    Document   : itemManagement
    Created on : 29 Apr 2023, 12:20:33 pm
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
        <title>Add Item</title>
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
                    <div class="pop-up-text"><%=addItemErr%></div>
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
                    New Item
                </div>
            </div>
            
            <div class="main-content">
                <div class="form-container">
                    <form action="AddItemController" method="POST" enctype="multipart/form-data">
                        <%
                            ArrayList<ArrayList<String>> categoryList = (ArrayList<ArrayList<String>>) session.getAttribute("categories");
                            String selectedCategory = (String) session.getAttribute("selectedCategory");
                        %>

                        <div>
                            <label for="category"><span class="asterik">*</span>Category: </label>
                            <select name="itemCategory" id="category" onchange="checkOption()">
                                <%
                                    for (ArrayList<String> category : categoryList) {
                                    if (!category.get(0).equals("All")) {
                                    String categoryName = category.get(0);
                                %>
                                <option value="<%=categoryName%>" <%=(categoryName.equals(selectedCategory)) ? "selected" : ""%>><%=categoryName%></option>
                                <% }} %>
                                
                                <option value="newCategory">---New Category---</option>
                                <input type="text" id="newCategory" name="newCategory" style="display: none;" placeholder="Enter new category"/>
                            </select>
                        </div>
                        <script>
                            function checkOption() {
                                document.getElementById("newCategory").style.display = (document.getElementById("category").value === "newCategory") ? "" : "none";
                            }
                        </script>
                                     
                        <div>
                            <label for="itemName"><span class="asterik">*</span>Item Name: </label>
                            <input type="text" id="itemName" name="itemName" placeholder="Item name. Max 100 characters" value="<%=(request.getParameter("itemName") != null) ? request.getParameter("itemName") : ""%>">
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
                            
                            window.onload = function() {
                                var imageInput = document.getElementById("image");
                                if (imageInput.files.length > 0) {
                                    loadFile({
                                        target: {
                                            files: [imageInput.files[0]]
                                        }
                                    });
                                }
                            };
                        </script>
                                
                        <div class="description-container">
                            <label for="description">Description: </label>
                            <textarea id="description" name="itemDescription" maxlength="500"><%=(request.getParameter("itemDescription") != null) ? request.getParameter("itemDescription") : ""%></textarea>
                        </div>
                                
                        <div>
                            <label for="price"><span class="asterik">*</span>Price: </label>
                            <input type="text" id="price" name="itemPrice" placeholder="Enter price" value="<%=(request.getParameter("itemPrice") != null) ? request.getParameter("itemPrice") : ""%>">
                        </div>
                                
                        <div>
                            <label for="quantity"><span class="asterik">*</span>Quantity: </label>
                            <input type="text" id="quantity" name="itemQuantity" placeholder="Enter in stock quantity" value="<%=(request.getParameter("itemQuantity") != null) ? request.getParameter("itemQuantity") : ""%>">
                        </div>
                        
                        <div class="separator"></div>
                        
                        <div class="save-container">
                            <input class="add-product-button" type="submit" value="Save & return">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

