<%-- 
    Document   : index
    Created on : 9 May 2023, 2:51:29 am
    Author     : angus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="staff-login">
            <div class="login-button">
                <a href="ItemManagementController">Login as Staff</a>
            </div>
        </div> 
        
        <div class="user-login">
            <div class="login-button">
                <a href="CustomerProductListController">Login as User</a>
            </div>
        </div>
        
        <jsp:include page="/ConnServlet" flush="true" />

    </body>
</html>
