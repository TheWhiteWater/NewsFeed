package nz.co.redice.newsfeeder.repository.local.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import nz.co.redice.newsfeeder.repository.local.DateFormatter;
import nz.co.redice.newsfeeder.repository.local.TextFormatter;

@Entity
public class Entry {
    public String source;
    public String author;
    public String title;
    public String description;
    public String url;
    public String urlToImage;
    private Long publishedAt;
    public String content;
    private String category;

    @PrimaryKey(autoGenerate = true)
    public int uuid;


    public Entry(String source, Object author, String title, String description,
                 String url, String urlToImage, String publishedAt, String content) {
        this.source = source;
        if (author != null) {
            this.author = author.toString();
        } else this.author = "";
        this.title = TextFormatter.cutOfSourceName(title, source);
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = DateFormatter.convertStringToLong(publishedAt);
        this.content = content;
    }

    public Entry() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getPublishedAt() {
        return publishedAt;
    }

    public String getPublishedDateTime() {
        return DateFormatter.getPublishedDateTime(publishedAt);
    }

    public String getPublishedAgo() {
        return DateFormatter.getTimeAgo(publishedAt);
    }

    public void setPublishedAt(Long publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return uuid == entry.uuid &&
                Objects.equals(source, entry.source) &&
                Objects.equals(author, entry.author) &&
                Objects.equals(title, entry.title) &&
                Objects.equals(description, entry.description) &&
                Objects.equals(url, entry.url) &&
                Objects.equals(urlToImage, entry.urlToImage) &&
                Objects.equals(publishedAt, entry.publishedAt) &&
                Objects.equals(content, entry.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, author, title, description, url, urlToImage, publishedAt, content, uuid);
    }
}
