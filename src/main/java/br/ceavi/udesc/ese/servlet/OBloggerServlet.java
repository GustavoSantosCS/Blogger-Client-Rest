package br.ceavi.udesc.ese.servlet;

import br.ceavi.udesc.ese.model.Blog;
import br.ceavi.udesc.ese.model.OAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/blog"})
public class OBloggerServlet extends HttpServlet {

    private static final long serialVersionUID = 3825046275024129443L;

    private OAuth oAuth;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (chequeSession(request, response)) return;

        final String URL = "https://www.googleapis.com/blogger/v3/users/self/blogs?key=" + oAuth.getApiKey();
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(URL);

        Response resp = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + oAuth.getToken()).get();

        String json = resp.readEntity(String.class);
        JsonParser parser = new JsonParser();
        JsonElement parsed = parser.parse(json);
        JsonObject asJsonObject = parsed.getAsJsonObject();
        JsonArray blogs = asJsonObject.getAsJsonArray("items");

        List<Blog> listBlog = new ArrayList<>();
        if (blogs != null) {
            for (JsonElement blog : blogs) {
                String blogID = blog.getAsJsonObject().get("id").getAsString();
                String blogName = blog.getAsJsonObject().get("name").getAsString();
                String blogUrl = blog.getAsJsonObject().get("url").getAsString();
                listBlog.add(new Blog(blogID, blogName, blogUrl));
            }
            request.setAttribute("BLOGS", listBlog);
        }

        request
                .getRequestDispatcher("/WEB-INF/pages/blog/index.jsp")
                .forward(request, response);


    }

    private boolean chequeSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        oAuth = (OAuth) request.getSession().getAttribute("OAUTH");
        if (oAuth == null || oAuth.getToken() == null || oAuth.getToken().equals("")) {

            request.getSession().invalidate();
            response.sendRedirect("/blogger-client");
            return true;
        }
        return false;
    }
}
