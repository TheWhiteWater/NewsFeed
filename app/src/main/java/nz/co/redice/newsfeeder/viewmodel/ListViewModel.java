package nz.co.redice.newsfeeder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nz.co.redice.newsfeeder.model.Article;
import nz.co.redice.newsfeeder.model.ShowEntry;
import nz.co.redice.newsfeeder.networking.NewsService;
import nz.co.redice.newsfeeder.networking.NewsServiceFactory;

import static io.reactivex.internal.operators.observable.ObservableBlockingSubscribe.subscribe;

public class ListViewModel extends AndroidViewModel {

    private MutableLiveData<ShowEntry> headlines = new MutableLiveData<>();
    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private Disposable disposableSearch;
    private Disposable disposableTopHeaders;
    NewsService newsService = NewsServiceFactory.create();
    private String country = "nz";
    private String apiKey = "dd08d6df03c34eaeb649bdbf5dcfd6f7";

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ShowEntry> getHeadlines() {
        return headlines;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    private void searchFor(String keyWord) {
        disposableSearch = newsService.requestAllHeadlines(keyWord, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .map(h -> h.getArticles())
                .flatMap(s -> Observable.fromIterable(s))
                .subscribe(s -> convertToShowEntry(s));
    }


    public void loadTopHeaders() {
        requestHeadlines()
                .subscribe(s -> convertToShowEntry(s));


    }

    void convertToShowEntry(Article article) {
        ShowEntry dummy = new ShowEntry(
                article.getSource().getName(),
                article.getAuthor(),
                article.getTitle(),
                article.getDescription(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getPublishedAt(),
                article.getContent());


        updateLiveData(dummy);
    }

    private void updateLiveData(ShowEntry dummy) {
        headlines.setValue(dummy);
        loading.setValue(false);
        error.setValue(false);
    }



    private Observable<Article> requestHeadlines() {
        return newsService
                .requestTopHeadlines(country, apiKey)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(h -> h.getArticles())
                .flatMap(Observable::fromIterable);
    }




    @Override
    protected void onCleared() {
        super.onCleared();
        disposableSearch.dispose();
        disposableTopHeaders.dispose();
    }

}
