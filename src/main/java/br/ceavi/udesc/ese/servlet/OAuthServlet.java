package br.ceavi.udesc.ese.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.blogger.BloggerScopes;

import br.ceavi.udesc.ese.model.OAuth;

@WebServlet(urlPatterns = { "/auth" })
public class OAuthServlet extends AbstractAuthorizationCodeServlet {

    private static final long serialVersionUID = 5483786041416456286L;

    private OAuth oAuth;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String clientId = request.getParameter("client-id");
        String clientKey = request.getParameter("client-key");
        String apiKey = request.getParameter("api-key");
    
        oAuth = new OAuth(apiKey, clientId, clientKey, "");
        request.getSession().setAttribute("OAUTH", oAuth);
        
        super.service(request, response);
    }

    @Override
    protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
        GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath("/blogger-client/oauth2callback");
        return url.build();
    }

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws IOException {
     
        List<String> scopes = Arrays.asList(BloggerScopes.BLOGGER);
        return new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                oAuth.getClientId(), oAuth.getClientKey(), scopes).setAccessType("online").setApprovalPrompt("auto").build();
    }

    @Override
    protected String getUserId(HttpServletRequest request) throws ServletException, IOException {
        return ((OAuth) request.getSession().getAttribute("OAUTH")).getClientId();
    }

}
