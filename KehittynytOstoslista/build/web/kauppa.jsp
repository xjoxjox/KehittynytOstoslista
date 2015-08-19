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
                        <td>${kauppa.bonus.nimi}</td>
                        <td>
                            <form method="post" name="frm" action="KaupanMuokkausNakymaServLet">
                                <input type="hidden" name="id" value="${kauppa.id}">
                                <input  type="hidden" name="hakunimi" value="${hakunimi}">
                                <input  type="hidden" name="hakukaupunki" value="${hakukaupunki}">
                                <input  type="hidden" name="hakubonus" value="${hakubonus}">
                                <input type="submit" value="Muokkaa">
                            </form>
                        </td>                        
                        <td>
                            <form method="post" name="frm" action="KaupanpoistoServLet">
                                <input type="hidden" name="id" value="${kauppa.id}">
                                <input  type="hidden" name="hakunimi" value="${hakunimi}">
                                <input  type="hidden" name="hakukaupunki" value="${hakukaupunki}">
                                <input  type="hidden" name="hakubonus" value="${hakubonus}">
                                <input type="submit" value="Poista">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </c:if><br>
        <c:if test="${muokkauskauppa != null}">
            <form method="post" name="frm" action="KaupanMuokkausServLet">
                <input  type="hidden" name="id" value="${muokkauskauppa.id}">
                <input  type="hidden" name="hakunimi" value="${hakunimi}">
                <input  type="hidden" name="hakukaupunki" value="${hakukaupunki}">
                <input  type="hidden" name="hakubonus" value="${hakubonus}">
                <table>
                    <tr><td><h3>${muokkauskauppa.nimi} ${muokkauskauppa.kaupunki}</h3></td></tr>
                    <tr><td><b>Nimi</b></td><td>: <input  type="text" name="muokkausnimi" value="${muokkauskauppa.nimi}"></td></tr>        
                    <tr><td><tr><td ><b>Kaupunki</b></td><td>: <input  type="text" name="muokkauskaupunki" value="${muokkauskauppa.kaupunki}"></td></tr>
                    <tr><td><b>Osoite</b></td><td>: <input  type="text" name="muokkausosoite" value="${muokkauskauppa.osoite}"></td></tr>
                    <tr><td><b>Bonus</b></td>
                    <td>: <select name="muokkausbonus">
                        <c:forEach items="${bonukset}" var="bonus">
                            <option value="${bonus.id}">${bonus.nimi}</option>
                        </c:forEach>  
                    </td>
                </tr>
                </table>
                <input type="submit" value="Muokkaa">
            </form>       
        </c:if>
        <c:if test="${muokkausnimiviesti != null}">
            <p>${muokkausnimiviesti}</p>
        </c:if>
        <c:if test="${muokkauskaupunkiviesti != null}">
            <p>${muokkauskaupunkiviesti}</p>
        </c:if>
        <c:if test="${muokkausosoiteviesti != null}">
            <p>${muokkausosoiteviesti}</p>
        </c:if>
        <c:if test="${muokkausbonusviesti != null}">
            <p>${muokkausbonusviesti}</p>
        </c:if>
        <c:if test="${viesti != null}">
            <p>${viesti}</p>
        </c:if>
            <c:if test="${poistoviesti != null}">
                <br><p>${poistoviesti}</p><br>
            </c:if>
    </html>
</t:pohja>
