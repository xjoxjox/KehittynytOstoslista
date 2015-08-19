<%-- 
    Document   : kaupat
    Created on : Aug 6, 2015, 11:04:00 AM
    Author     : Johanna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<t:pohja pageTitle="Kaupat">
    <html>
        <head>
        </head>
        <body>
            <br/><br/>
            <form method="post" name="frm" action="KauppaServLet">
                <p>Haku toimii tällä hetkellä syöttämällä vain yksi hakukriteeri. Jättämällä kaikki tyhjäksi haetaan kaikki kaupat.</p>
            <table>
                <tr><td><h3>Etsi kauppa</h3></td></tr>
                <tr><td><b>Kaupunki</b></td><td>: <input  type="text" name="hakukaupunki"></td></tr>        
                <tr><td><b>Nimi</b></td><td>: <input  type="text" name="hakunimi"></td></tr>  
                <tr><td><b>Bonus</b></td>
                    <td>: <select name="hakubonus">
                        <c:forEach items="${bonukset}" var="bonus">
                            <option value="${bonus.id}">${bonus.nimi}</option>
                        </c:forEach>  
                    </td>
                </tr> 
                <tr><td><input  type="submit" value="Hae"></td></tr>
            </table>
            </form><br>
            <form method="post" name="frm" action="KaupanlisaysServLet">
            <table>
                <tr><td><h3>Lisää kauppa</h3></td></tr>
                <tr><td><b>Nimi</b></td><td>: <input  type="text" name="nimi"></td></tr>        
                <tr><td><tr><td ><b>Kaupunki</b></td><td>: <input  type="text" name="kaupunki"></td></tr>
                <tr><td><b>Osoite</b></td><td>: <input  type="text" name="osoite"></td></tr> 
                <tr><td><b>Bonus</b></td>
                    <td>: <select name="hakubonus">
                        <c:forEach items="${bonukset}" var="bonus">
                            <option value="${bonus.id}">${bonus.nimi}</option>
                        </c:forEach>  
                    </td> 
                <tr><td><input  type="submit" value="Lisää"></td></tr>
            </table>
            </form>
            <c:if test="${lisaysviesti != null}">
                    <br><p>${lisaysviesti}</p><br>
            </c:if>
        </body>
    </html>
</t:pohja>