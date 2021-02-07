package br.ceavi.udesc.ese.servlet;

import br.ceavi.udesc.ese.model.Author;
import br.ceavi.udesc.ese.model.Blog;
import br.ceavi.udesc.ese.model.OAuth;
import br.ceavi.udesc.ese.model.Post;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/postagens"})
public class PostOfBlogServlet extends HttpServlet {

    private static final long serialVersionUID = -8109475717496411673L;

    private OAuth oAuth;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().removeAttribute("BLOGS");

        if (checkSession(request, response)) return;
        String blogId = request.getParameter("blogId");

        if (blogId != null) {
            request.setAttribute("BLOG", getBlog(blogId));
            request.setAttribute("POSTS", getPostOfBlog(blogId));

            request
                    .getRequestDispatcher("/WEB-INF/pages/posts/index.jsp")
                    .forward(request, response);
        } else {
            response.sendRedirect("/blogger-client/blog");
        }
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

    private Blog getBlog(String blogId) {
        final String URL = "https://www.googleapis.com/blogger/v3/blogs/" + blogId + "?key=" + oAuth.getApiKey();
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(URL);

        String json = webTarget.request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(String.class);

        JsonElement blogJson = new JsonParser().parse(json);
        String blogID = blogJson.getAsJsonObject().get("id").getAsString();
        String blogName = blogJson.getAsJsonObject().get("name").getAsString();
        String blogUrl = blogJson.getAsJsonObject().get("url").getAsString();

        return new Blog(blogID, blogName, blogUrl);
    }

    private List<Post> getPostOfBlog(String blogId) {
        final String URL = "https://www.googleapis.com/blogger/v3/blogs/" + blogId + "/posts?key=" + oAuth.getApiKey();
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(URL);

        Response resp = webTarget.request(MediaType.APPLICATION_JSON_TYPE).get();

        String json = resp.readEntity(String.class);
        JsonParser parser = new JsonParser();
        JsonElement parsed = parser.parse(json);
        JsonObject asJsonObject = parsed.getAsJsonObject();
        JsonArray posts = asJsonObject.getAsJsonArray("items");

        List<Post> listPost = new ArrayList<>();
        if (posts != null)
            for (JsonElement postJson : posts) {
                String postId = postJson.getAsJsonObject().get("id").getAsString();
                String postTitle = postJson.getAsJsonObject().get("title").getAsString();
                String postUrl = postJson.getAsJsonObject().get("url").getAsString();
                String postContent = postJson.getAsJsonObject().get("content").getAsString();
                LocalDateTime postPublished = parseDate(postJson.getAsJsonObject().get("published").getAsString());
                LocalDateTime postUpdated = parseDate(postJson.getAsJsonObject().get("updated").getAsString());

                Post post = new Post(postId, postUrl, postTitle, postContent, postPublished, postUpdated);
                JsonElement authorJson = postJson.getAsJsonObject().get("author");

                String authorId = authorJson.getAsJsonObject().get("id").getAsString();
                String authorDisplayName = authorJson.getAsJsonObject().get("displayName").getAsString();
                String authorUrl = authorJson.getAsJsonObject().get("url").getAsString();
                String authorImage = authorJson.getAsJsonObject().get("image").getAsJsonObject().get("url")
                        .getAsString();

                Author author = new Author(authorId, authorDisplayName, authorUrl, authorImage);
                post.setAuthor(author);
                listPost.add(post);
            }
        return listPost;
    }

    private LocalDateTime parseDate(String date) {
        ZonedDateTime result = ZonedDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
        return result.toLocalDateTime();
    }
}
