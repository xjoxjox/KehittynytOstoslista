<%-- 
    Document   : tuotteet
    Created on : Aug 6, 2015, 11:03:21 AM
    Author     : Johanna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<t:pohja pageTitle="Tuotteet">
    <html>
        <head>
        </head>
        <body>
            <br/><br/>
            <form method="post" name="frm" action="TuoteServLet">
                <p>Haku toimii tällä hetkellä vain nimen perusteella.</p>
            <table>
                <tr><td><h3>Etsi tuotetta</h3></td></tr>
                <tr><td><b>Nimi</b></td><td>: <input  type="text" name="hakunimi"></td></tr>
                <tr><td><b>Valmistaja</b></td><td>: <input  type="text" name="hakuvalmistaja"></td></tr> 
                <tr><td><input  type="submit" value="Hae"></td></tr>
            </table>
            </form><br>
            <form method="post" name="frm" action="TuotteenlisaysServLet">
            <table>
                <tr><td><h3>Lisää tuote</h3></td></tr>
                <tr><td><b>Nimi</b></td><td>: <input  type="text" name="nimi"></td></tr>        
                <tr><td><tr><td ><b>Valmistaja</b></td><td>: <input  type="text" name="valmistaja"></td></tr>
                <tr><td><b>Paino</b></td><td>: <input  type="text" name="paino"></td></tr>
                <tr><td><b>Kauppa</b></td>
                    <td>: <select name="kauppa">
                        <c:forEach items="${kaupat}" var="kauppa">
                            <option value="${kauppa.id}">${kauppa.nimi} ${kauppa.kaupunki}</option>
                        </c:forEach>  
                    </td>
                </tr>
                <tr><td><b>Hinta</b></td><td>: <input type="text" name="hinta"></td></tr>
                <tr><td><input  type="submit" value="Lisää"></td></tr>
            </table>
            </form>
            <c:if test="${lisaysviesti != null}">
                    <br><p>${lisaysviesti}</p><br>
            </c:if>
            <c:if test="${hintalisaysviesti != null}">
                    <br><p>${hintalisaysviesti}</p><br>
            </c:if>
        </body>
    </html>
</t:pohja>