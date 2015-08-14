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
        <c:if test="${eiOstoslistoja != null}">
            <br><p>${eiOstoslistoja}</p><br>
        </c:if>
        <c:if test="${eiOstoslistoja == null}">  
            <table>
                <thead>
                    <tr>
                        <th>Nimi</th>
                        <th>Tallennettu</th>
                        <th>Summa</th>
                        <th>Paino</th>
                        <th>Kauppa</th>
                        <th>Tuotteet</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="ostoslista" items="${listat}">
                    <tr>
                        <td>${ostoslista.nimi}</td>
                        <td>${ostoslista.paivays}</td>
                        <td>${ostoslista.summa}</td>
                        <td>${ostoslista.paino}</td>
                        <td>${ostoslista.kauppa}</td>
                        <td>
                            <form method="post" name="frm" action="OstoslistaTuoteidenhakuServLet">
                                <input type="hidden" name="id" value="${ostoslista.id}">
                                <input type="submit" value="Hae tuotteet">
                            </form>
                        </td>
                        <td>
                            <form method="post" name="frm" action="OstoslistaTuotteenlisaysServLet">
                                <input type="hidden" name="id" value="${ostoslista.id}">
                                <input type="submit" value="Lisää tuote">
                            </form>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <form method="post" name="frm" action="OstoslistanlisaysServLet">
            <table>
                <tr><td><h3>Luo uusi lista</h3></td></tr>
                <tr><td><b>Nimi</b></td><td>: <input  type="text" name="listanimi"></td></tr>        
                <tr><td><b>Kauppa</b></td>
                    <td>: <select name="kauppa">
                        <c:forEach items="${kaupat}" var="kauppa">
                            <option value="${kauppa.id}">${kauppa.nimi} ${kauppa.kaupunki}</option>
                        </c:forEach>  
                    </td>
                </tr>
            </table>
            <input type="submit" value="Luo uusi lista">
        </form>
        <c:if test="${listanluonti != null}">
            <br><p>${listanluonti}</p><br>
        </c:if>
    </body>
    </html>
</t:pohja>

