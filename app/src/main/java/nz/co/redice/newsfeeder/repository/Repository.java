package nz.co.redice.newsfeeder.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nz.co.redice.newsfeeder.repository.local.AppDatabase;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;
import nz.co.redice.newsfeeder.repository.local.dao.EntryDao;
import nz.co.redice.newsfeeder.repository.remote.NewsService;
import nz.co.redice.newsfeeder.repository.remote.RetrofitFactory;
import nz.co.redice.newsfeeder.repository.remote.model.Article;
import nz.co.redice.newsfeeder.repository.utils.Constants;

public class Repository {

    private static Repository instance;
    private final AppDatabase mDatabase;
    private static NewsService mNewsService;
    private static EntryDao mDao;

    private Repository(Application application) {
        mDatabase = Room.databaseBuilder(application, AppDatabase.class, Constants.DATABASE)
                .fallbackToDestructiveMigration()
                .build();

        mDao = mDatabase.mEntryDao();
        mNewsService = RetrofitFactory.create();
    }

    public static Repository getInstance(Application application) {
        if (instance == null)
            instance = new Repository(application);
        return instance;
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
                })
                .subscribe(s -> mDao.insertEntry(s));
    }

    public LiveData<List<Entry>> retrieveByCategory(String category) {
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
