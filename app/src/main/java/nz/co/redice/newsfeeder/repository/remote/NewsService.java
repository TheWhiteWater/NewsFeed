package nz.co.redice.newsfeeder.repository.remote;


import io.reactivex.Single;
import nz.co.redice.newsfeeder.repository.remote.model.Headlines;
import retrofit2.http.GET;
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
            @Query("category") String category);


}
