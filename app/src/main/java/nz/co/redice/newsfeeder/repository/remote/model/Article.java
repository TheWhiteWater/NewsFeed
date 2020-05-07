package nz.co.redice.newsfeeder.repository.remote.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import nz.co.redice.newsfeeder.repository.local.dao.Entry;

public class Article {

    @SerializedName("source")
    @Expose
    private Source source;
    @SerializedName("author")
    @Expose
    private Object author;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("urlToImage")
    @Expose
    private String urlToImage;
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;
    @SerializedName("content")
    @Expose
    private String content;


    public Article(Source source, Object author, String title, String description, String url,
                   String urlToImage, String publishedAt, String content) {
        super();
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public Source getSource() {
        return source;
    }


    public Object getAuthor() {
            return author;
    }


    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }


    public String getUrl() {
        return url;
    }


    public String getUrlToImage() {
        return urlToImage;
    }


    public String getPublishedAt() {
        return publishedAt;
    }


    public String getContent() {
        return content;
    }



    public  Entry toEntry() {
        return new Entry(
                this.getSource().getName(),
                this.getAuthor(),
                this.getTitle(),
                this.getDescription(),
                this.getUrl(),
                this.getUrlToImage(),
                this.getPublishedAt(),
                this.getContent());
    }
}
