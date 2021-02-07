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
    
    <link rel="stylesheet" href="/blogger-client/css/styles.css">
</head>
    <% 
        OAuth oAuth = (OAuth) request.getSession().getAttribute("OAUTH");
        if (oAuth == null || oAuth.getToken() == null || oAuth.getToken().equals("")) {

            request.getSession().invalidate();
            response.sendRedirect("/blogger-client");
            return;
        }
         Blog blog = (Blog) request.getAttribute("BLOG");
    %>
<body>
    <div class="container">
        <div class="row">
            
            <div class="col-6 mx-auto">
    
                <h1 class="text-center m-5">Todos os Post</h1>
                    <div class="my-3 text-center">
                            <a href="/blogger-client/postagens/new?blogId=<%=blog.getId()%>" class="btn btn-primary btn-lg" type="submit" >Nova Publicação</a>
                    </div>
                <% 
                   
                    List<Post> posts = (List<Post>) request.getAttribute("POSTS");
                    if(posts != null)
                        for (Post post : posts) {
                %>
                            <section class="mb-3">
                                <div class="card text-center">
                                    <div class="card-header">
                                        <%=post.getAuthor().getDisplayName()%>
                                    </div>
                                    <div class="card-body">
                                        <h5 class="card-title">
                                            <%=post.getTitle()%>
                                        </h5>
                                        <p class="card-text">
                                            <%=post.getContent()%>    
                                        </p>
                                    </div>
                                    <div class="card-footer text-muted">
                                        Criado em: <cite title="Source Title"><%=post.getFormatPublished()%></cite>
                                    </div>
                                </div>
                            </section>
                <%  
                        } 
                %>
            </div>
        </div>
    </div>
</body>

</html>
