<%-- 
    Document   : ostoslistat
    Created on : Jul 31, 2015, 6:23:56 PM
    Author     : Johanna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<t:pohja pageTitle="Ostoslistat">
    <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        </head>
    <body>
        <table>
            <thead>
                <tr>
                    <th>Nimi</th>
                    <th>Tallennettu</th>
                    <th>Summa</th>
                    <th>Paino</th>
                    <th>Kauppa</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="ostoslista" items="${listat}">
                <tr>
                    <td><c:out value="${ostoslista.nimi}"  /></td>
                    <td><c:out value="${ostoslista.paivays}" /></td>
                    <td><c:out value="${ostoslista.summa}" /></td>
                    <td><c:out value="${ostoslista.paino}"  /></td>
                    <td><c:out value="${ostoslista.kauppa}"  /></td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
    </html>
</t:pohja>

