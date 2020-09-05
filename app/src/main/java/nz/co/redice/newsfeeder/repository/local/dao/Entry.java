package nz.co.redice.newsfeeder.repository.local.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import nz.co.redice.newsfeeder.repository.utils.DateFormatter;
import nz.co.redice.newsfeeder.repository.utils.TextFormatter;

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
    @PrimaryKey
    private int uuid;




    public Entry() {
    }

    public Entry(int hashCode, String source, Object author, String title, String description,
                 String url, String urlToImage, String publishedAt, String content) {
        this.uuid = hashCode;
        this.source = TextFormatter.getSourceTag(source);
        this.author = author != null ? author.toString() : "";
        this.description = description;
        this.title = TextFormatter.cutOfSourceName(title);
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = DateFormatter.convertStringToLong(publishedAt);
        this.content = content;

    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
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
        return source.equals(entry.source) &&
                title.equals(entry.title) &&
                url.equals(entry.url) &&
                Objects.equals(urlToImage, entry.urlToImage) &&
                publishedAt.equals(entry.publishedAt) &&
                category.equals(entry.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url, publishedAt, category);
    }
}
