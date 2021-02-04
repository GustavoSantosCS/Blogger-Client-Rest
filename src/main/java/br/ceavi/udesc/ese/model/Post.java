package br.ceavi.udesc.ese.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Post implements Serializable {

    private static final long serialVersionUID = 7081473484305455525L;

    private String id;
    private String url;
    private String title;
    private String content;
    private LocalDateTime published;
    private LocalDateTime updated;

    private Author author;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm", Locale.US);

    public Post() {
        this.id = "";
        this.url = "";
        this.title = "";
        this.content = "";
        this.published = null;
        this.updated = null;
    }

    public Post(String id, String url, String title, String content, LocalDateTime published, LocalDateTime updated) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.content = content;
        this.published = published;
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public Author getAuthor() {
        return author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormatPublished() {
        return getPublished().format(formatter);
    }

    public String getFormatUpdated() {
        return getUpdated().format(formatter);
    }

    public LocalDateTime getPublished() {
        return published;
    }

    public void setPublished(LocalDateTime published) {
        this.published = published;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
