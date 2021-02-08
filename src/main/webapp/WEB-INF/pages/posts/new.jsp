<%@page import="br.ceavi.udesc.ese.model.OAuth"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html lang="pt-br">

<head>
    <title>Blogger Rest Client</title>
    <meta content="text/html; charset=utf-8" />
    
    <link rel="stylesheet" href="/blogger-client/css/styles.css">
</head>
    <% 
        OAuth oAuth = (OAuth) request.getSession().getAttribute("OAUTH");
        if (oAuth == null || oAuth.getToken() == null || oAuth.getToken().equals("")) {

            request.getSession().invalidate();
            response.sendRedirect("/blogger-client");
            return;
        }
    %>
<body>
    <div class="container">
        <div class="row">
            <h1 class="mt-3 mb-5">Nova Postagem</h1>

            <%
                if(request.getAttribute("NEW_POST_RESPONSE_ERRO") != null){
                    out.println("<p class='text-danger'>Não foi possível criar o post</p>");
                    String message = (String) request.getAttribute("NEW_POST_RESPONSE_ERRO_MESSAGE");
                    out.println("<p class='text-danger'>" + message + "</p>");
                    request.removeAttribute("NEW_POST_RESPONSE_ERRO");
                    request.removeAttribute("NEW_POST_RESPONSE_ERRO_MESSAGE");
                }
            %>

            <form class="col-md-6 mx-auto" action="/blogger-client/postagens/new" method="post">
                <input type="hidden" name="blogId" value="<%=request.getParameter("blogId")%>">
                <div class="mb-3">
                  <label for="title" class="form-label">Titulo: </label>
                  <input type="text" name="title" class="form-control" id="title" required>
                </div>
                <div class="mb-3">   
                    <label for="content">Corpo: </label>
                    <textarea name="content" class="form-control" placeholder="Corpo da Postagem"style="height: 100px"></textarea>
                </div>
                <div class="mt-3 text-center">
                    <div>
                        <a href="/blogger-client/postagens?blogId=<%=request.getParameter("blogId")%>" class="btn btn-danger">Voltar</a>
                        <button type="submit" class="btn btn-success" >Publicar</button>
                    </div>
                </div>
           
              </form>
        </div>
    </div>

</body>
</html>