package nz.co.redice.newsfeeder.networking;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsServiceFactory {

    public static final String BASE_URL = "https://newsapi.org/v2/";
    private static Retrofit instance;


    private NewsServiceFactory() {

        HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(mLoggingInterceptor)
                .build();

        instance = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

    }

    public static NewsService create() {
        if (instance == null) {
            new NewsServiceFactory();
        }
        return instance.create(NewsService.class);
    }
}
