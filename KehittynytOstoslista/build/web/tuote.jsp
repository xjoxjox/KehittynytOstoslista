<%-- 
    Document   : tuote
    Created on : Aug 6, 2015, 12:01:21 PM
    Author     : Johanna
--%>

<%@page import="KehittynytOstoslista.Models.Tuote"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="tuote" items="${tuotteet}">
                    <tr>
                        <td>${tuote.nimi}</td> 
                        <td>${tuote.valmistaja}</td> 
                        <td>${tuote.paino}</td>
                        <td>
                            <form method="post" name="frm" action="TuotteenmuokkausServLet">
                                <input type="hidden" name="id" value="${tuote.id}">
                                <input type="submit" value="Muokkaa">
                            </form>
                        </td>                        
                        <td>
                            <form method="post" name="frm" action="TuotteenpoistoServLet">
                                <input type="hidden" name="id" value="${tuote.id}">
                                <input type="submit" value="Poista">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </c:if>
        <c:if test="${viesti != null}">
            <p>${viesti}</p>
        </c:if>
            <c:if test="${poistoviesti != null}">
                <br><p>${poistoviesti}</p><br>
            </c:if>
    </html>
</t:pohja>