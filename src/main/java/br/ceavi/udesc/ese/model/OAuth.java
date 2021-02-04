package br.ceavi.udesc.ese.model;

import java.io.Serializable;

public class OAuth implements Serializable {
    
    private static final long serialVersionUID = 4847866263458324994L;
    
    private String apiKey;
    private String clientId;
    private String clientKey;
    private String token;

    public OAuth(String apiKey, String clientId, String clientKey, String token){
        this.apiKey = apiKey;
        this.clientId = clientId;
        this.clientKey = clientKey;
        this.token = token;
    }

    public OAuth() {
        this.apiKey = "";
        this.clientId = "";
        this.clientKey = "";
        this.token = "";
    }

    public String getApiKey() {
        return apiKey;
    }
    
    public String getClientId() {
        return clientId;
    }

    public String getClientKey() {
        return clientKey;
    }

    public String getToken() {
        return token;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
