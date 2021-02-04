package br.ceavi.udesc.ese.model;

import java.io.Serializable;

public class Author implements Serializable {
    
    private static final long serialVersionUID = 4757233099723692476L;
    
    private String id;
    private String displayName;
    private String url;
    private String image;

    public Author() {
        this.id = "";
        this.displayName = "";
        this.url = "";
        this.image = "";
    }

    public Author(String id, String displayName, String url, String image) {
        this.id = id;
        this.displayName = displayName;
        this.url = url;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}