package nz.co.redice.newsfeeder.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import nz.co.redice.newsfeeder.repository.local.AppDatabase;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;
import nz.co.redice.newsfeeder.repository.local.dao.EntryDao;
import nz.co.redice.newsfeeder.repository.remote.NewsService;
import nz.co.redice.newsfeeder.repository.remote.RetrofitFactory;
import nz.co.redice.newsfeeder.repository.remote.model.Article;
import nz.co.redice.newsfeeder.repository.utils.Constants;
import nz.co.redice.newsfeeder.view.presentation.Category;

public class Repository {

    private static Repository instance;
    private static NewsService mNewsService;
    private static EntryDao mDao;
    private final AppDatabase mDatabase;

    private Repository(Application application) {
        mDatabase = AppDatabase.getInstance(application);
        mDao = mDatabase.mEntryDao();
        mNewsService = RetrofitFactory.create();
        Completable.fromAction(mDao::deleteAllEntries)
                .subscribeOn(Schedulers.io());
        startRequestingCategories(Constants.CATEGORIES);
    }

    public static Repository getInstance(Application application) {
        if (instance == null)
            instance = new Repository(application);
        return instance;
    }

    public void startRequestingCategories(List<Category> list) {
        for (Category c : list) {
            Observable.interval(0, 5, TimeUnit.MINUTES)
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .subscribe(x -> requestCategory(c.getTag()));
        }

    }

    public void requestCategory(String category) {
        mNewsService.requestByCategory(Constants.COUNTRY, Constants.API_KEY, category)
                .subscribeOn(Schedulers.io())
                .toObservable()
                .flatMap(s -> Observable.fromIterable(s.getArticles()))
                .map(Article::toEntry)
                .map(s -> {
                    s.setCategory(category);
                    return s;
                }).subscribe(s -> mDao.insertEntry(s));
    }

    public Observable<List<Entry>> retrieveByCategory(String category) {
        return mDao.getAllEntries(category);
    }

    public LiveData<Entry> retrieveEntryById(int uuid) {
        return mDao.getEntry(uuid);

    }

    public void clearDatabase() {
        Completable.fromAction(mDao::deleteAllEntries)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
