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
            <table>
                <tr><td>
                <h3>Etsi tuotetta</h3></td></tr>
                <tr><td ><b>Tuote</b></td>
                <td>: <input  type="text" name="haku">
                </td></tr>        
                <tr><td>
                <input  type="submit" value="Hae"></td></tr>
            </table>
            </form>
        </body>
    </html>
</t:pohja>