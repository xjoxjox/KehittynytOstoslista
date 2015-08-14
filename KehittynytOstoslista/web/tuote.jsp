<%-- 
    Document   : tuote
    Created on : Aug 6, 2015, 12:01:21 PM
    Author     : Johanna
--%>

<%@page import="KehittynytOstoslista.Models.Kauppa"%>
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
                    <th>Hinta</th>
                    <th></th>
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
                            <form method="post" name="frm" action="HintojenhakuServLet">
                                <input type="hidden" name="id" value="${tuote.id}">
                                <input type="submit" value="Hae hinnat">
                            </form>
                        </td>
                        <td>
                            <form method="post" name="frm" action="TuoteHintaNakymaServlet">
                                <input type="hidden" name="id" value="${tuote.id}">
                                <input type="submit" value="Päivitä hinta">
                            </form>
                        </td>
                        <td>
                            <form method="post" name="frm" action="TuotteenMuokkausNakymaServLet">
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
        <c:if test="${hakunimi == null}">
            <p>Tuotteita yhteensä ${tuoteLkm}.</p>
        </c:if>
        </c:if>
        <c:if test="${hinnat != null}">
            <h3>${tuote.nimi} ${tuote.valmistaja}</h3>
            <table>
            <thead>
                <tr>
                    <th>Kauppa</th>
                    <th>Hinta</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="hinta" items="${hinnat}">
                    <tr>
                        <td>${hinta.key.nimi} ${hinta.key.kaupunki}</td> 
                        <td>${hinta.value}</td> 
                </c:forEach>
            </tbody>
        </c:if>
        <c:if test="${hinnanpaivitys != null}">
            <form method="post" name="frm" action="HintaServLet">
                <input  type="hidden" name="id" value="${hinnanpaivitysTuote.id}">
                <input  type="hidden" name="hakunimi" value="${hakunimi}">
                <table>
                    <tr><td><h3>${hinnanpaivitysTuote.nimi} ${hinnanpaivitysTuote.valmistaja}</h3></td></tr>
                    <tr><td><b>Kauppa</b></td>
                        <td>:  <select name="kauppa">
                            <c:forEach items="${kaupat}" var="kauppa">
                                <option value="${kauppa.id}">${kauppa.nimi} ${kauppa.kaupunki}</option>
                            </c:forEach>  
                        </td>       
                    <tr><td><tr><td ><b>Uusi hinta</b></td><td>: <input  type="text" name="uusihinta"></td></tr>
                </table>
                <input type="submit" value="Päivitä hinta">
            </form>       
        </c:if>
        <c:if test="${muokkausTuote != null}">
            <form method="post" name="frm" action="TuotteenmuokkausServlet">
                <input  type="hidden" name="id" value="${muokkausTuote.id}">
                <input  type="hidden" name="hakunimi" value="${hakunimi}">
                <table>
                    <tr><td><h3>${muokkausTuote.nimi} ${muokkausTuote.valmistaja}</h3></td></tr>
                    <tr><td><b>Nimi</b></td><td>: <input  type="text" name="muokkausnimi" value="${muokkausTuote.nimi}"></td></tr>        
                    <tr><td><tr><td ><b>Valmistaja</b></td><td>: <input  type="text" name="muokkausvalmistaja" value="${muokkausTuote.valmistaja}"></td></tr>
                    <tr><td><b>Paino</b></td><td>: <input  type="text" name="muokkauspaino" value="${muokkausTuote.paino}"></td></tr>
                </table>
                <input type="submit" value="Muokkaa">
            </form>       
        </c:if>
        <c:if test="${nimionnistui != null}">
            <p>${nimionnistui}</p>
        </c:if>
        <c:if test="${valmistajaonnistui != null}">
            <p>${valmistajaonnistui}</p>
        </c:if>
        <c:if test="${painonnistui != null}">
            <p>${painonnistui}</p>
        </c:if>
        <c:if test="${viesti != null}">
            <p>${viesti}</p>
        </c:if>
        <c:if test="${poistoviesti != null}">
                <br><p>${poistoviesti}</p><br>
        </c:if>
        <c:if test="${hintahakuviesti != null}">  
            <br><h3>${tuote.nimi} ${tuote.valmistaja}</h3><br>
            <p>${hintahakuviesti}</p><br>
        </c:if>
        <c:if test="${uusihinta != null}">
                <br><p>${uusihinta}</p><br>
        </c:if>
    </html>
</t:pohja>