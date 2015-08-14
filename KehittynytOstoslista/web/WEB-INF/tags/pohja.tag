<%@tag description="Generic template for Ostoslista pages" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag import="KehittynytOstoslista.Models.Kayttaja"%>
<%@attribute name="pageTitle"%>
<!DOCTYPE html>
<html>
    <head>
        <title>${pageTitle}</title>
        <link href="http://t-xjoxjox.users.cs.helsinki.fi/KehittynytOstoslista/css/bootstrap.css" rel="stylesheet">
        <link href="http://t-xjoxjox.users.cs.helsinki.fi/KehittynytOstoslista/css/bootstrap-theme.css" rel="stylesheet">
        <link href="http://t-xjoxjox.users.cs.helsinki.fi/KehittynytOstoslista/css/main.css" rel="stylesheet">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%  session = request.getSession(false);
            if (session == null || session.getAttribute("kirjautunut") == null) {
               RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
               dispatcher.forward(request, response);
            } %>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-3">
                    <h2 class="offscreen">Sivunavigaatio</h2>
                    <div id="hmenu">
                        <ul>
                            <li><a href="OstoslistaTallennettuServLet">Ostoslistat</a></li>
                            <li><a href="KauppojenhakuServLet?param=tuotteet.jsp">Tuotteet</a></li>
                            <li><a href="BonustenhakuServLet?param=kaupat.jsp">Kaupat</a></li>
                            <li><a href="maksutavat.jsp">Maksutavat</a></li>
                            <li><a href="bonukset.jsp">Bonukset</a></li>
                            <li><a href="ostohistoria.jsp">Ostohistoria</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <c:if test="${pageError != null}">
                <div class="alert alert-danger">${pageError}</div>
            </c:if>
            <br>
            <form method="link" action="logoutprocess.jsp">
                <input type="submit" value="Kirjaudu ulos">
            </form>
            <br>
            <jsp:doBody/>
        </div>
    </body>
</html>