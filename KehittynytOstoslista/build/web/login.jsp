<%-- 
    Document   : login
    Created on : Jul 30, 2015, 3:33:22 PM
    Author     : Johanna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body bgcolor="khaki">
        <div id="login">
            <h1 text-align="center">OSTOSLISTAT</h1>
            <br>
            <form action="Login" method="POST">
                Käyttäjänimi: <input type="text" name="username" value="${kayttaja}" />
                Salasana: <input type="password" name="password" value="${salasana}"/>
                <button type="submit">Kirjaudu</button>
            </form> 
            <br>
            <c:if test="${virhe != null}">
                <div class="alert alert-danger">Virhe! ${virhe}</div>
            </c:if>
        </div>
    </body>
</html>
