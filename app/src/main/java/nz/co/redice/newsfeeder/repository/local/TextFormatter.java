package nz.co.redice.newsfeeder.repository.local;

public class TextFormatter {


    public static String getSourceTag(String source) {
        if (source.contains(".co.nz")) {
            String[] subs = source.split(".co.nz");
            source = subs[0];
        }
        return source;
    }

    public static String cutOfSourceName(String title, String tag) {

        if (tag.contains(".co")) {
            String[] subs = tag.split(".co");
            tag = subs[0];
        }

        String subs[] = title.split(" - ");
        if (subs[1].contains(tag) ||
                subs[1].contains("1News") ||
                subs[1].contains("New Zealand Herald")
        ) {
            title = subs[0];
        }
        return title;
    }
}
