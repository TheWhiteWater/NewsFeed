package nz.co.redice.newsfeeder.repository.utils;

import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {


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
        } else if (diff < 24 * HOUR_MILLIS) {
            if (diff / HOUR_MILLIS < 2)
                return "an hour ago";
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }

    }


    public static Long convertStringToLong(String publishedAt) {
        Date instant = new Date();
        try {
            instant = ISO8601Utils.parse(publishedAt, new ParsePosition(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instant.getTime();
    }


    public static String getPublishedDateTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, HH:mm aa");
        Date date = new Date(time);

        return formatter.format(date);
    }
}
