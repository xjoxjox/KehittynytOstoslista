<%-- 
    Document   : index
    Created on : 22.7.2015, 23:51:14
    Author     : Johanna
--%>

<%@page import="KehittynytOstoslista.Models.Tuote"%>
<%@page import="KehittynytOstoslista.Models.Kayttaja"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Testilistaus</title>
    </head>
    <body>
        <h1>Testilista</h1>
        <h2>Käyttäjät</h2>
        <% List<Kayttaja> kayttajat = Kayttaja.getKayttajat();
        
        for (Kayttaja kayttaja : kayttajat) { %>
            <li> <% out.println(kayttaja.getTunnus() + " " );
        }
        %><h2>Tuotteet</h2><%
         List<Tuote> tuotteet = Tuote.getTuotteet();
        
        for (Tuote tuote : tuotteet) { %>
            <li> <% out.println(tuote.getNimi() + " " + tuote.getValmistaja() + " " + tuote.getPaino());
        }
        %>
    </body>
</html>

