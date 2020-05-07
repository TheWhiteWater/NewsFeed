package nz.co.redice.newsfeeder.networking;


import io.reactivex.Single;
import nz.co.redice.newsfeeder.model.Headlines;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsService {


    @GET("top-headlines")
    Single<Headlines> requestTopHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey);

    @GET("/v2/everything")
    Single<Headlines> search(
            @Query("q") String keyWord,
            @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Single<Headlines> requestByCategory(
            @Query("country") String country,
            @Query("apiKey") String apiKey,
            @Query("health") String category);


}
