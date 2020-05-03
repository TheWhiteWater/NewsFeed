package nz.co.redice.newsfeeder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nz.co.redice.newsfeeder.model.Article;
import nz.co.redice.newsfeeder.model.Entry;
import nz.co.redice.newsfeeder.networking.NewsService;
import nz.co.redice.newsfeeder.networking.NewsServiceFactory;
import nz.co.redice.newsfeeder.utils.AppDatabase;


public class ListViewModel extends AndroidViewModel {

    private MutableLiveData<Entry> headlines = new MutableLiveData<>();
    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private AppDatabase db = Room.databaseBuilder(getApplication(),
            AppDatabase.class, "headlines").build();
    private final CompositeDisposable mDisposable = new CompositeDisposable();


    private Disposable disposableSearch;
    private Disposable disposableTopHeaders;
    NewsService newsService = NewsServiceFactory.create();
    private String country = "nz";
    private String apiKey = "dd08d6df03c34eaeb649bdbf5dcfd6f7";

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Entry> getHeadlines() {
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
                .subscribe(s -> s.toEntry());
    }


    public void gimmeSomeAction() {
        newsService.requestTopHeadlines(country, apiKey).toObservable()
                .subscribeOn(Schedulers.io())
                .flatMap(s -> Observable.fromIterable(s.getArticles()))
                .map(Article::toEntry)
//                .doOnComplete(this::loadLiveData)
                .subscribe(s -> db.mEntryDao().insertEntry(s));
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s -> updateLifeData(s));

        loadLiveData();

    }


    public void loadLiveData() {
        db.mEntryDao().getAllEntries()
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::fromIterable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    headlines.setValue(s);
                    error.setValue(false);
                    loading.setValue(false);
                });
    }


    void updateLifeData(Entry s) {
        headlines.setValue(s);
        error.setValue(false);
        loading.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.dispose();
    }

}
