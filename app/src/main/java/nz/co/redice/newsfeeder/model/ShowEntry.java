package nz.co.redice.newsfeeder.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.ParsePosition;
import java.util.Date;
import java.util.Objects;

public class ShowEntry {

    public final String source;
    public final String author;
    public final String title;
    public final String description;
    public final String url;

    public final String urlToImage;
    private Date publishedAt;
    public final String content;

    public int uuid;

    public ShowEntry(String source, Object author, String title, String description,
                     String url, String urlToImage, String publishedAt, String content) {
        this.source = source;
        if (author != null) {
            this.author = author.toString();
        } else this.author = "";
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        try {
            this.publishedAt = ISO8601Utils.parse(publishedAt, new ParsePosition(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.content = content;
    }

    public String getPublishedAgo() {
        return getTimeAgo(publishedAt.getTime());
    }


    public static String getTimeAgo(long time) {
        final int SECOND_MILLIS = 1000;
        final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        final int DAY_MILLIS = 24 * HOUR_MILLIS;

        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public Date getPublishedAt() {
        return publishedAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowEntry showEntry = (ShowEntry) o;
        return uuid == showEntry.uuid &&
                source.equals(showEntry.source) &&
                author.equals(showEntry.author) &&
                title.equals(showEntry.title) &&
                description.equals(showEntry.description) &&
                url.equals(showEntry.url) &&
                urlToImage.equals(showEntry.urlToImage) &&
                publishedAt.equals(showEntry.publishedAt) &&
                content.equals(showEntry.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, author, title, description, url, urlToImage, publishedAt, content, uuid);
    }
}
