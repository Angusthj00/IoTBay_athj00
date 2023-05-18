<%-- 
    Document   : NavbarCustomer
    Created on : 15 May 2023, 1:23:29 pm
    Author     : angus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="navbar">
    <div class="navbar-menu"> 
        <div class="navbar-logo">
            IoTBay
        </div>
        <div class="navbar-main">
            <div class="search-container">
                <form class="search-form" action="CustomerSearchItemController" method="GET">
                    <img src="images/search.png" alt="search"/>
                    <input type="text" id="searchInput" name="searchInput" placeholder="Search by product name">
                    <input class="search-button" type="submit" value="Search">
                </form>
            </div> 
            <div class="navbar-item" onclick="window.location.href='ShipmentManagementController'">
                <img src="images/order.png" alt="your orders"/>
                Your Orders
            </div>
            <div class="navbar-item" style="position: relative;">
                <img src="images/shopping-cart.png" alt="cart"/>
                Cart
                <div class="cart-item-count">1</div>
            </div>
        </div>
        <div class="navbar-profile">
            <img src="images/user.png" alt="user profile"/>
        </div>
    </div>
        
    <div class="category-bar">
        <%
            String userSelectedCategory = (String) session.getAttribute("userSelectedCategory");
            ArrayList<ArrayList<String>> categoryList = (ArrayList<ArrayList<String>>) session.getAttribute("userCategories");

            if (categoryList != null) {
            for (ArrayList<String> category : categoryList) {
            String categoryName = category.get(0);
            //String categoryCount = category.get(1);
        %>
        <a href="CustomerProductListController?category=<%=categoryName%>">
            <div class="category-item <%=(categoryName.equals(userSelectedCategory) ? "active-tab" : "")%>">
                <%=categoryName%>
            </div>
        </a>

        <% }} %>
    </div>

    
</div>

