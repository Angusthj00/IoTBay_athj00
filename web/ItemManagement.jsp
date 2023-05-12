<%-- 
    Document   : ItemManagement
    Created on : 30 Apr 2023, 4:18:53 pm
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
        <title>Item Management</title>
    </head> 
    <body>
        <!--navbar here-->
        <%@include file="Navbar.jsp"%>
        
        <div class="content">
            <!--pop up message here-->
            <%
                
                String popupMsg = (String) session.getAttribute("popupMsg");
                if (popupMsg != null) {
            %>
            <div id="popupContainer" class="pop-up-message-container slide-in">
                <div class="indicator"></div>
                <div class="indicator-icon">
                    <img src="images/tick.png" alt="indicator-icon"/>
                </div>
                <div class="pop-up-content">
                    <div class="pop-up-title">Success</div>
                    <div class="pop-up-text"><%=popupMsg%></div>
                </div>
            </div>
            <script>
                var popupDiv = document.getElementById("popupContainer");           
                setTimeout(function() {
                    popupDiv.classList.add("slide-out");  
                }, 3000);
            </script>
            <%
                session.removeAttribute("popupMsg");
                }
            %>

            <div class="header">
                <div class="header-left">
                    <div class="header-title">
                        Catalogue Management
                    </div>
                    <div class="header-description">
                        Make changes to your item inventory, prices and photos easily.
                    </div>
                </div>
                
                <div class="header-right">
                    <form class="search-form" action="SearchItemController" method="GET">
                        <img src="images/search.png" alt="search"/>
                        <input type="text" id="searchInput" name="searchInput" placeholder="Search by product name">
                        <input class="search-button" type="submit" value="Search">
                    </form>
                </div>
            </div>
            
            <div class="main-content">
                <div class="list-container">
                    <div class="list-menu">
                        <div class="tab-group">
                            <div class="tab-group-header">
                                <img src="images/storyboard.png" alt="category"/>
                                Category                                
                            </div>
                            <div class='tab-group-content'>
                                <%
                                    String selectedCategory = (String) session.getAttribute("selectedCategory");
                                    ArrayList<ArrayList<String>> categoryList = (ArrayList<ArrayList<String>>) session.getAttribute("categories");
                                    
                                    if (categoryList != null) {
                                    for (ArrayList<String> category : categoryList) {
                                    String categoryName = category.get(0);
                                    //String categoryCount = category.get(1);
                                %>
                                
                                <a href="ItemManagementController?category=<%=categoryName%>">
                                    <div class="tab-item <%=(categoryName.equals(selectedCategory) ? "active-tab" : "")%>">
                                        <%=categoryName%>
                                    </div>
                                </a>
                                
                                <%
                                    }
                                    }
                                %>
    
                            </div>
                        </div>
                    </div>
                    
                    <div class="list-table">
                        <div class="table-actions-container">
                            <%
                                String selectedSort = (String) session.getAttribute("selectedSort");
                            %>
                            <div class="sort">
                                <%
                                    if (selectedSort != null) {
                                %>
                                <div class="sort-indicator"></div>
                                
                                <% } %>
                                <img src="images/sort.png" alt="sort"/>
                                <div class="dropdown">
                                    <div class="dropdown-trigger" onclick="toggleDropdown()">
                                        Sorting management
                                    </div>
                                    <div class="dropdown-content">
                                        <%
                                            ArrayList<String> sortOptions = new ArrayList<>();
                                            sortOptions.add("Price low to high");
                                            sortOptions.add("Price high to low");
                                            sortOptions.add("In stock low to high");
                                            sortOptions.add("In stock high to low");

                                            for (String option : sortOptions) {
                                        %>
                                        <a href="SortItemController?sort=<%=option%>">
                                            <div class="dropdown-content-item <%=(selectedSort != null && option.equals(selectedSort)) ? "active-sort" : ""%>">
                                                <%=option%>
                                            </div>
                                        </a>
                                            
                                        <% } %>
                                    </div>                                                                   
                                </div>
                            </div>
                            
                            <script>
                                function toggleDropdown() {
                                    var dropdownContent = document.querySelector(".dropdown-content");
                                    dropdownContent.style.display = dropdownContent.style.display === "block" ? "none" : "block";
                                }
                            </script>
                            
                            <div class="add-new" onclick="window.location.href='AddItem.jsp'">
                                <span class="add-new-icon">+</span>Add New
                            </div>
                        </div>
                            
                        <table>
                            <%
                                ArrayList<Item> itemList = (ArrayList<Item>) session.getAttribute("items");
                                if (itemList.isEmpty()) {
                                    String noItemError = (String) session.getAttribute("noItemErr");
                            %>       
                            <tr><td><%=noItemError%></td></tr>
                            
                            <% } else { %>
                            <tr class="table-header">
                                <td colspan="2" style="width: 40%; padding-left: 15px;">Product</td>
                                <td style="width: 20%;">Category</td>
                                <td style="width: 10%;">In Stock</td>
                                <td style="width: 15%;">Price</td>
                                <td style="width: 15%; text-align: right; padding-right: 15px;">Action</td>
                            </tr>
                            <%
                                for (Item item : itemList) {
                            %>
                            <tr class="table-content">
                                <td style="width: 8%; padding-left: 15px;"><img style="height: 60px; width: auto;" src="images/itemDemoImage.jpg" alt="demo item"></td>
                                <td><%=item.getName()%></td>
                                <td><%=item.getCategory()%></td>
                                <td><%=item.getQuantity()%></td>
                                <td style="font-size: 14px; color: red;">A$<%=item.getPrice()%></td>
                                <td style="text-align: right; padding-right: 15px;">
                                    <a href="EditItem.jsp?name=<%=item.getName()%>&category=<%=item.getCategory()%>&image=<%=item.getImage()%>&description=<%=item.getDescription()%>&price=<%=item.getPrice()%>&quantity=<%=item.getQuantity()%>">
                                        Edit
                                    </a> | 
                                    <a href="#" onclick="showConfirmation('<%=item.getName()%>')" >
                                        Delete
                                    </a>
                                </td>     
                            </tr>
                            
                            <% }} %>
                            <script>
                                function showConfirmation(itemName) {
                                    document.getElementById("item-to-delete").innerHTML = itemName;
                                    document.getElementById("confirmation-container-overlay").style.display = "block";
                                    document.getElementById("confirmation-container").style.display = "block";
                                }
                            </script>
                        </table>

                    </div>
                </div>
            </div>
        </div>
                            
        <!--delete item-->
        <div id="confirmation-container-overlay" style="display: none"></div>
        <div id="confirmation-container" style="display: none">
            <p>Are you sure you want to delete "<span id="item-to-delete"></span>"? </p>
            <div class="button-group">
                <div class="cancel-button" onclick="cancelDelete()">Cancel</div>
                <div class="delete-button" onclick="deleteItem()">Delete</div>
            </div>
        </div>                  
        <script>
            function deleteItem() {
                var itemName = document.getElementById("item-to-delete").innerHTML;
                window.location.href = "DeleteItemController?itemName=" + encodeURIComponent(itemName);
            }
            function cancelDelete() {
                document.getElementById("confirmation-container-overlay").style.display = "none";
                document.getElementById("confirmation-container").style.display = "none";
            }
        </script>
            
    </body>
</html>
