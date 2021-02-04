package br.ceavi.udesc.ese.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.blogger.BloggerScopes;

@WebServlet(urlPatterns = { "/oauth2callback" })
public class OAuthCallbackServlet extends AbstractAuthorizationCodeCallbackServlet {

    private static final long serialVersionUID = -4075141469130952740L;

    private String clientId, clientKey;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        clientId = (String) request.getSession().getAttribute("client-id");
        clientKey = (String) request.getSession().getAttribute("client-key");
        super.doGet(request, response);
    }

    @Override
    protected void onSuccess(HttpServletRequest request, HttpServletResponse response, Credential credential)
            throws ServletException, IOException {
        request.getSession().setAttribute("token", credential.getAccessToken());
        try {
            response.sendRedirect("/blogger-client");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onError(HttpServletRequest request, HttpServletResponse response,
            AuthorizationCodeResponseUrl errorResponse) throws ServletException, IOException {
        request.getSession().invalidate();
        try {
            response.sendRedirect("/blogger-client");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getRedirectUri(HttpServletRequest request) throws ServletException, IOException {
        GenericUrl url = new GenericUrl(request.getRequestURL().toString());
        url.setRawPath("/blogger-client/oauth2callback");
        return url.build();
    }

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws IOException {
        List<String> scopes = Arrays.asList(BloggerScopes.BLOGGER);

        return new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                clientId, clientKey, scopes).setAccessType("online").setApprovalPrompt("auto").build();
    }

    @Override
    protected String getUserId(HttpServletRequest request) throws ServletException, IOException {
        return (String) request.getSession().getAttribute("client-id");
    }
}