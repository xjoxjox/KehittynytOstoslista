<%-- 
    Document   : logoutprocess
    Created on : Aug 6, 2015, 10:47:44 AM
    Author     : Johanna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><body bgcolor="khaki" text-aling="center">
        <%
            session.invalidate();
        %>
        <h1><font color="Red">Olet nyt kirjautunut ulos.</font></h1>
        <a href="login.jsp">Kirjaudu uudelleen sisään</a>
    </body>
</html>