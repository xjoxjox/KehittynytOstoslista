<%-- 
    Document   : tuote
    Created on : Aug 6, 2015, 12:01:21 PM
    Author     : Johanna
--%>

<%@page import="KehittynytOstoslista.Models.Tuote"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<t:pohja pageTitle="Tuote">
    <html>
        <c:if test="${viesti == null}">
        <table>
            <thead>
                <tr>
                    <th>Tuote</th>
                    <th>Valmistaja</th>
                    <th>Paino</th>
                </tr>
            </thead>
            <tbody>
                ${tuotteet}
                <c:forEach var="tuote" items="${tuotteet}">
                    <tr>
                        <td>${tuote.nimi}</td> 
                        <td>${tuote.valmistaja}</td> 
                        <td>${tuote.paino}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </c:if>
        <c:if test="${viesti != null}">
            <p>${viesti}</p>
        </c:if>
    </html>
</t:pohja>