package br.ceavi.udesc.ese.servlet;

import br.ceavi.udesc.ese.model.OAuth;
import br.ceavi.udesc.ese.model.PostCreate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(urlPatterns = {"/postagens/new"})
public class AddPostOfBlogServlet extends HttpServlet {

    static final long serialVersionUID = -3940147985686093737L;

    static final String NEW_POST_RESPONSE = "NEW_POST_RESPONSE";
    OAuth oAuth;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (checkSession(request, response)) return;

        String blogId = request.getParameter("blogId");
        if (blogId != null) {
            request
                    .getRequestDispatcher("/WEB-INF/pages/posts/new.jsp")
                    .forward(request, response);
        } else {
            response.sendRedirect("/blogger-client/blog");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (checkSession(request, response)) return;

        String blogId = request.getParameter("blogId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        PostCreate newPost = new PostCreate();
        newPost.setTitle(title);
        newPost.setContent(content);

        // Adicionar o post
        final String URL = "https://www.googleapis.com/blogger/v3/blogs/" + blogId + "/posts/";
        Client client = ClientBuilder.newClient();

        Entity<PostCreate> json = Entity.json(newPost);

        Response resp = client.target(URL).request().header(HttpHeaders.AUTHORIZATION, "Bearer " + oAuth.getToken())
                .post(json);

        if (resp.getStatus() == 200) {
            request.getSession().setAttribute(NEW_POST_RESPONSE, "Post Criado Com Sucesso");
        } else {
            request.getSession().setAttribute(NEW_POST_RESPONSE, "Erro ao Criar o Post");
            request.getSession().setAttribute("ERRO", Arrays.asList(
                    resp.getStatusInfo().toString(),
                    resp.getStatus(),
                    resp.readEntity(String.class)
            ));
        }

        response.sendRedirect("/blogger-client/postagens?blogId=" + blogId);
    }


    private boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        oAuth = (OAuth) request.getSession().getAttribute("OAUTH");
        if (oAuth == null || oAuth.getToken() == null || oAuth.getToken().equals("")) {

            request.getSession().invalidate();
            response.sendRedirect("/blogger-client");
            return true;
        }
        return false;
    }

}
