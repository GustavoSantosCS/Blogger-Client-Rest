<%@page import="br.ceavi.udesc.ese.model.OAuth"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html lang="pt-br">

<head>
    <title>Blogger Rest Client</title>
    <meta content="text/html; charset=utf-8" />
</head>

<body>
    <h1>Autentificado</h1>
    <h2>Client Rest Api</h2>
  
    <% 
        OAuth oAuth = (OAuth) request.getSession().getAttribute("OAUTH"); 
    %>

    <h3><%=oAuth.getApiKey()%></h3>
    <h3><%=oAuth.getClientId()%></h3>
    <h3><%=oAuth.getClientKey()%></h3>
    <h3><%=oAuth.getToken()%></h3>
</body>

</html>
