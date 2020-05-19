package nz.co.redice.newsfeeder.repository.utils;

public class TextFormatter {


    public static String getSourceTag(String source) {
        if (source.contains(".")) {
            String[] subs = source.split("\\.");
            source = subs[0];
        }
        return source;
    }

    public static String cutOfSourceName(String title) {

        StringBuilder sb = new StringBuilder();

        String[] subs = title.split("-");
        sb.append(subs[0]);
        if (subs.length > 2 ) {
            for (int i = 1; i < subs.length - 1; i++) {
                sb.append(subs[i]);
            }

        }
        return sb.toString();
    }
}
