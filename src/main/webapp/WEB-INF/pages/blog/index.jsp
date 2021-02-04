<%@page import="br.ceavi.udesc.ese.model.Blog"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html lang="pt-br">

<head>
    <title>Blogger Rest Client</title>
    <meta content="text/html; charset=utf-8" />
</head>

<body>
    <h1>Todos os Blogs</h1>
    <% 
        List<Blog> blogs = (List<Blog>) request.getSession().getAttribute("BLOGS");
        if(blogs != null)
            for (Blog blog : blogs) {
    %>
                <a href="<%=blog.getUrl()%>">
                    <%=blog.getName()%>
                </a>  
    <%  
            } 
    %>

    <% if(blogs == null || (blogs != null && blogs.isEmpty())) {%>
        <h2>Vocé não possui nenhum blog cadastrado!</h2>
        <p>Crie um blog! Para poder usar a aplicação</p>
    <% } %>
</body>

</html>
