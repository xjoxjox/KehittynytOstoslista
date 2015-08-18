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
                        <td>
                            <form method="post" name="frm" action="OstoslistaTuotteetServLet">
                                <input type="hidden" name="id" value="${ostoslista.id}">
                                <input type="submit" value="Hae tuotteet">
                            </form>
                        </td>
                        <td>
                            <form method="post" name="frm" action="OstoslistaLisaaTuoteNakymaServLet">
                                <input type="hidden" name="id" value="${ostoslista.id}">
                                <input type="submit" value="Lisää tuote">
                            </form>
                        </td>
                        <td>
                            <form method="post" name="frm" action="OstoslistankuittausServLet">
                                <input type="hidden" name="id" value="${ostoslista.id}">
                                <input type="hidden" name="summa" value="${ostoslista.summa}">
                                <input type="hidden" name="paino" value="${ostoslista.paino}">
                                <input type="hidden" name="kuittikauppa" value="${ostoslista.kauppa}">
                                <input type="submit" value="Kuittaa ostetuksi">
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
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${tuotteet}" var="tuote">
                        <tr>
                            <td>${tuote.key.nimi} ${tuote.key.valmistaja} ${tuote.key.paino}kg</td>
                            <td>${tuote.value}</td>
                            <td>
                                <form method="post" name="frm" action="OstoslistaTuotteenpoistoServLet">
                                    <input type="hidden" name="listaid" value="${listaid}">
                                    <input type="hidden" name="tuoteid" value="${tuote.key.id}">
                                    <input type="submit" value="Poista tuote">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>       
                </tbody>
            </table><br>
        </c:if> 
        <c:if test="${tuotteenpoisto != null}">
            <br><p>${tuotteenpoisto}</p><br>
        </c:if>
        <c:if test="${tuotelistatyhja != null}">
            <br><p>${tuotelistatyhja}</p><br>
        </c:if>
        <c:if test="${tuotteenlisays != null}">
            <form method="post" name="frm" action="OstoslistaLisaaTuote">
                <input type="hidden" name="lista" value="${listaid}">
            <table>
                <tr><td><h3>Lisää tuote listalle</h3></td></tr>
                <tr><td><b>Tuote</b></td><td>: <select name="tuote">
                        <c:forEach items="${tuotelista}" var="tuote">
                            <option value="${tuote.id}">${tuote.nimi} ${tuote.valmistaja}</option>
                        </c:forEach>  </td></tr>        
                <tr><td><b>kpl</b></td>
                    <td>:   <select name="maara">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10</option>
                            </select>
                    </td>
                </tr>
            </table>
                <input type="submit" value="Lisää tuote">
        </form>
        </c:if><br>
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
        <c:if test="${lisays != null}">
            <br><p>${lisays}</p><br>
        </c:if>
        <c:if test="${listanluonti != null}">
            <br><p>${listanluonti}</p><br>
        </c:if>
    </body>
    </html>
</t:pohja>

