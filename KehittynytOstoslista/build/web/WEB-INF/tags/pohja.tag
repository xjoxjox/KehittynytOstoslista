<%@tag description="Generic template for Ostoslista pages" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag import="KehittynytOstoslista.Models.Kayttaja"%>
<%@attribute name="pageTitle"%>
<!DOCTYPE html>
<html>
    <head>
        <title>${pageTitle}</title>
        <link href="../../css/bootstrap.css" rel="stylesheet">
        <link href="../../css/bootstrap-theme.css" rel="stylesheet">
        <link href="../../css/main.css" rel="stylesheet">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <% //  session = request.getSession(false);
           //  if (session == null || session.getAttribute("kirjautunut") == null) {
           //     RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
           //     dispatcher.forward(request, response);
           // } %>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-3">
                    <h2>Navi</h2>
                    <div class="panel panel-default">
                        <ul class="nav">
                            <li><a href="ostoslistat.html">Ostoslistat</a></li>
                            <li><a href="tuotteet.html">Tuotteet</a></li>
                            <li><a href="kaupat.html">Kaupat</a></li>
                            <li><a href="maksutavat.html">Maksutavat</a></li>
                            <li><a href="bonus.html">Bonukset</a></li>
                            <li><a href="ostohistoria.html">Ostohistoria</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <c:if test="${pageError != null}">
                <div class="alert alert-danger">${pageError}</div>
            </c:if>
            <jsp:doBody/>
        </div>
    </body>
</html>