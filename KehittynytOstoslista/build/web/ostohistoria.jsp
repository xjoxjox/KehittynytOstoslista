<%-- 
    Document   : ostohistoria
    Created on : Aug 6, 2015, 11:04:07 AM
    Author     : Johanna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<t:pohja pageTitle="Ostohistoria">
    <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        </head>
    <body>
        <c:if test="${viesti != null}">
            <br><p>${viesti}</p><br>
        </c:if>
        <c:if test="${viesti == null}">  
            <table>
                <thead>
                    <tr>
                        <th>Nimi</th>
                        <th>Kuitattu</th>
                        <th>Summa €</th>
                        <th>Paino kg</th>
                        <th>Kauppa</th>
                        <th>Bonus</th>
                        <th></th>
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
                        <td>${ostoslista.kauppa.nimi} ${ostoslista.kauppa.kaupunki}</td>
                        <td>${ostoslista.bonus.nimi}</td>
                        <td>
                            <form method="post" name="frm" action="OstoslistaTuotteetServLet?param=ostohistoria.jsp">
                                <input type="hidden" name="id" value="${ostoslista.listaid}">
                                <input type="submit" value="Hae tuotteet">
                            </form>
                        </td>
                        <td>
                            <form method="post" name="frm" action="OstoslistaKuitattuPoistoServLet">
                                <input type="hidden" name="id" value="${ostoslista.id}">
                                <input type="submit" value="Poista">
                            </form>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table><br>
        </c:if>
            <c:if test="${tuotehaku != null}">
            <table>
                <thead>
                    <tr>
                        <th>Tuote</th>
                        <th>kpl</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${tuotteet}" var="tuote">
                        <tr>
                            <td>${tuote.key.nimi} ${tuote.key.valmistaja} ${tuote.key.paino}kg</td>
                            <td>${tuote.value}</td>
                        </tr>
                    </c:forEach>       
                </tbody>
            </table><br>
        </c:if>
        <c:if test="${tuotelistatyhja != null}">
            <br><p>${tuotelistatyhja}</p><br>
        </c:if>
    </body>
    </html>
</t:pohja>
