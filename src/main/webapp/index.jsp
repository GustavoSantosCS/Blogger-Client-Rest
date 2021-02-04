<%@page import="br.ceavi.udesc.ese.model.OAuth"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html lang="pt-br">

<head>
    <title>Blogger Rest Client</title>
    <meta content="text/html; charset=utf-8" />
</head>

<body>
    <h1>Bem Vindo!</h1>
    <h2>Client Rest Api</h2>
  
    <% 
        OAuth oAuth = (OAuth) request.getSession().getAttribute("OAUTH"); 
        if(oAuth != null) { 
            if(oAuth.getToken() == ""){
                request.getSession().invalidate();
            } else {
                response.sendRedirect("/blogger-client/blogs");
            }
        } 
    %>
    
    <%  
        if(oAuth == null) { 
    %>
            <form id="formulario_cliente" class="text-left" method="GET" action="auth" >
                <p>É necessário realizar a autentificação perante o Google</p>
                <fieldset title="Dados da OAuth 2.0">
                    <legend>Dados Da OAuth 2.0</legend>
                    <div>
                        <label for="client-id">Client ID:*</label>
                        <input id="client-id" type="text" name="client-id" required>
                    </div>
                    <div>
                        <label for="client-key">Client Secret:*</label>
                        <input id="client-key" type="text" name="client-key" required>
                    </div>
                </fieldset>
                <fieldset title="Dados da API">
                    <legend>Dados Da API</legend>
                    <div>
                        <label for="api-key">Api Key:*</label>
                        <input id="api-key" type="text" name="api-key" required>
                    </div>
                </fieldset>
                <button type="submit" >Autenticar</button>
            </form>
    <%  } %>
</body>

</html>