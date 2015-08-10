<%-- 
    Document   : kauppa
    Created on : Aug 10, 2015, 11:26:54 AM
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
                    <th>Kauppa</th>
                    <th>Kaupunki</th>
                    <th>Osoite</th>
                    <th>Bonus</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="kauppa" items="${kaupat}">
                    <tr>
                        <td>${kauppa.nimi}</td> 
                        <td>${kauppa.kaupunki}</td> 
                        <td>${kauppa.osoite}</td>
                        <td>${kauppa.bonusId}</td>
                        <td>
                            <form method="post" name="frm" action="KaupanmuokkausServLet">
                                <input type="hidden" name="id" value="${kauppa.id}">
                                <input type="submit" value="Muokkaa">
                            </form>
                        </td>                        
                        <td>
                            <form method="post" name="frm" action="KaupanpoistoServLet">
                                <input type="hidden" name="id" value="${kauppa.id}">
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
