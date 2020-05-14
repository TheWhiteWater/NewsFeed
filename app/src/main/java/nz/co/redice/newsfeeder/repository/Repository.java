package nz.co.redice.newsfeeder.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;
import nz.co.redice.newsfeeder.repository.local.dao.EntryDao;
import nz.co.redice.newsfeeder.repository.remote.NewsService;
import nz.co.redice.newsfeeder.repository.remote.model.Article;
import nz.co.redice.newsfeeder.repository.utils.Constants;

@Singleton
public class Repository {

    private final NewsService mNewsService;
    private final EntryDao mDao;

    @Inject
    Repository(NewsService service, EntryDao entryDao) {
        mDao = entryDao;
        mNewsService = service;
        Completable.fromAction(mDao::deleteAllEntries)
                .subscribeOn(Schedulers.io())
                .subscribe();
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

    public LiveData<List<Entry>> retrieveByCategory(String category) {
        return mDao.getAllEntries(category);
    }

    public Entry retrieveEntryById(int uuid) {
        return (Entry) mDao.getEntry(uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void clearDatabase() {
        Completable.fromAction(mDao::deleteAllEntries)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
