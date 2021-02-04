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
    %>

<body>
    <div class="center-body">
        <div class="box">
                <h1>Todos os Blogs</h1>
                
                <% 
                    List<Blog> blogs = (List<Blog>) request.getSession().getAttribute("BLOGS");
                        if(blogs != null)
                        for (Blog blog : blogs) {
                            %>
                                <div class="box-btn">
                                    <a href="/blogger-client/post?blogId=<%=blog.getId()%>">
                                        <%=blog.getName()%>
                                    </a>  
                                </div>
                            <%  
                        } 
                %>
                    
                <% if(blogs == null || (blogs != null && blogs.isEmpty())) {%>
                    <h2>Vocé não possui nenhum blog cadastrado!</h2>
                    <p>Crie um blog! Para poder usar a aplicação</p>
                <% } %>
        
        </div>
    </div>
</body>

