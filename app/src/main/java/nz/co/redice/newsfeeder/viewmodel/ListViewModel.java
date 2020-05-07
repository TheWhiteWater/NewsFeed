package nz.co.redice.newsfeeder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import nz.co.redice.newsfeeder.repository.local.AppDatabase;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;
import nz.co.redice.newsfeeder.repository.remote.NewsService;
import nz.co.redice.newsfeeder.repository.remote.RetrofitFactory;
import nz.co.redice.newsfeeder.repository.remote.model.Article;
import nz.co.redice.newsfeeder.utils.Category;


public class ListViewModel extends AndroidViewModel {

    private MutableLiveData<Entry> entry = new MutableLiveData<>();
    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();


    private AppDatabase db = Room.databaseBuilder(getApplication(),
            AppDatabase.class, "news_api.db").build();
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    private NewsService newsService = RetrofitFactory.create();
    private String country = "nz";
    private String apiKey = "dd08d6df03c34eaeb649bdbf5dcfd6f7";

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Entry> getEntry() {
        return entry;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }


    public void gimmeSomeNews(Category category) {
        // TODO: 5/4/2020   time intervals for ui update if idle
        // TODO: 5/4/2020   time scope for database cleaning

        fetchCategory(category);


    }

    private void fetchCategory(Category category) {
        requestCategory(category.toString());
        loadFromDatabase(category.toString());
    }

    private void requestTopHeadlines() {
        mDisposable.add(newsService.requestTopHeadlines(country, apiKey)
                .subscribeOn(Schedulers.io())
                .toObservable()
                .flatMap(s -> Observable.fromIterable(s.getArticles()))
                .map(Article::toEntry)
                .subscribe(s -> db.mEntryDao().insertEntry(s)));
    }

    private void requestCategory(String category) {
        mDisposable.add(newsService.requestByCategory(country, apiKey, category)
                .subscribeOn(Schedulers.io())
                .toObservable()
                .flatMap(s -> Observable.fromIterable(s.getArticles()))
                .map(Article::toEntry)
                .map(s -> {
                    s.setCategory(category);
                    return s;
                })
                .subscribe(s -> db.mEntryDao().insertEntry(s)));
    }


    public void loadFromDatabase(String category) {
        mDisposable.add((db.mEntryDao().getAllEntries(category))
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::fromIterable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> setLifeData(s)));
    }


    private void setLifeData(Entry s) {
        entry.setValue(s);
        error.setValue(false);
        loading.setValue(false);
    }

    private void clearDatabase() {
        mDisposable.add(Completable.fromAction(db.mEntryDao()::deleteAllEntries)
                .subscribeOn(Schedulers.io())
                .subscribe());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.dispose();
    }

}
