<%@page import="br.ceavi.udesc.ese.model.Author"%>
<%@page import="br.ceavi.udesc.ese.model.Post"%>
<%@page import="br.ceavi.udesc.ese.model.OAuth"%>
<%@page import="br.ceavi.udesc.ese.model.Blog"%>
<%@page import="java.util.List"%>
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
<body>
    <h1>Todos os Post</h1>
    <% 
        Blog blog = (Blog) request.getSession().getAttribute("BLOG");
        List<Post> posts = (List<Post>) request.getSession().getAttribute("POSTS");
        if(posts != null)
            for (Post post : posts) {
    %>
            <section>
                <div class="post">
                    <h2 class="post-title"><%=post.getTitle()%></h2>
                        <div class="post-author-container">
                            <h3>Autor:</h3>
                            <p class="post-author-name"><%=post.getAuthor().getDisplayName()%></p>
                            <p class="post-author-id"><%=post.getAuthor().getId()%></p>
                        </div>
                        <div class="post-data">
                            <p>Data de Criação: <%=post.getFormatPublished()%></p>
                            <p>Data do Ultima Atualiação: <%=post.getFormatUpdated()%></p>
                        </div>
                        <div class="post-content">
                            <%=post.getContent()%>
                        </div>
                </div>
            </section>
    <%  
            } 
    %>
</body>

</html>
