package nz.co.redice.newsfeeder.repository.utils;

import java.util.Arrays;
import java.util.List;

import nz.co.redice.newsfeeder.view.presentation.Category;

public class Constants {
    public final static String COUNTRY = "nz";
    public final static String API_KEY = "dd08d6df03c34eaeb649bdbf5dcfd6f7";

    public static final List<Category> CATEGORIES = Arrays.asList(
            Category.TOPS_HEADLINES,
            Category.BUSINESS,
            Category.ENTERTAINMENT,
            Category.HEALTH,
            Category.SCIENCE,
            Category.SPORTS,
            Category.TECHNOLOGY);

    public static final String DATABASE = "appDatabase";
}
