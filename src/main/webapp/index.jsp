<%@page import="br.ceavi.udesc.ese.model.OAuth"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html lang="pt-br">

<head>
    <title>Blogger Rest Client</title>
    <meta content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="css/styles.css">
</head>

    <% 
        OAuth oAuth = (OAuth) request.getSession().getAttribute("OAUTH"); 
        if(oAuth != null) { 
            if(oAuth.getToken() == null || oAuth.getToken().equals("")){
                request.getSession().invalidate();
            } else {
                response.sendRedirect("/blogger-client/blogs");
            }
        } 
    %>

<body>
    <div class="center-body">
        <div class="box">
                    <h1>Bem Vindo!</h1>
                    <h2>Client Rest Api</h2>
                    
                    <%  
                    if(oAuth == null) { 
                        %>
                        
                        <form id="formulario_cliente" method="GET" action="auth" >
                            <p>É necessário realizar a autentificação perante o Google</p>
                            <fieldset title="Dados da OAuth 2.0">
                            <legend>Dados Da OAuth 2.0</legend>
                            <div>
                                <label class="form-label" for="client-id">Client ID:*</label>
                                <input class="form-control" id="client-id" type="text" name="client-id" value="178025322854-87tm5i1nftuafnjnvf04ndm8empf6jja.apps.googleusercontent.com" required>
                            </div>
                            <div>
                                <label class="form-label" for="client-key">Client Secret:*</label>
                                <input class="form-control" id="client-key" type="password" name="client-key" value="cXbpi1eBLYLc4eJ20g7-Nnm7" required>
                            </div>
                        </fieldset>
                        <fieldset title="Dados da API">
                            <legend>Dados Da API</legend>
                            <div>
                                <label class="form-label" for="api-key">Api Key:*</label>
                                <input class="form-control" id="api-key" type="text" name="api-key" value="AIzaSyCwfAUOHRYkapb4eJIbYGnUm148ty6DKpQ" required>
                            </div>
                        </fieldset>
                        <div class="mt-3 text-center">
                            <button class="btn btn-primary btn-lg" type="submit" >Autenticar</button>
                        </div>
                    </form>
                    <%  } %>
                </div>
            </div>
       </body>
    
</html>