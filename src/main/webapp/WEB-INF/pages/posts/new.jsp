<%@page import="br.ceavi.udesc.ese.model.OAuth"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html lang="pt-br">

<head>
    <title>Blogger Rest Client</title>
    <meta content="text/html; charset=utf-8" />
</head>
    <% 
        OAuth oAuth = (OAuth) request.getSession().getAttribute("OAUTH");
        if (oAuth == null || oAuth.getToken() == null || oAuth.getToken().equals("")) {

            request.getSession().invalidate();
            response.sendRedirect("/blogger-client");
            return;
        }
    %>
        <%=request.getSession().getAttribute("ERRO")%>
<body>

    <form action="/blogger-client/post/new" method="post">
        <p>É necessário realizar a autentificação perante o Google</p>
        <fieldset title="Novo Post">
            <legend>Novo Post</legend>
            <input type="hidden" name="blogId" value="<%=request.getParameter("blogId")%>">
            <div>
                <label for="title">Titulo:*</label>
                <input id="title" type="text" name="title" required>
            </div>
            <div>
                <label for="content">Corpo</label>
                <input id="content" type="text" name="content"  required>
            </div>
        </fieldset>
        <div class="btn-group">
            <a href="post" class="btn">Voltar</a>
            <button type="submit" >Postar</button>
        </div>
    </form>

</body>
</html>