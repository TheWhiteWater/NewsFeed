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
import nz.co.redice.newsfeeder.dao.Entry;
import nz.co.redice.newsfeeder.model.Article;
import nz.co.redice.newsfeeder.networking.NewsService;
import nz.co.redice.newsfeeder.networking.RetrofitFactory;
import nz.co.redice.newsfeeder.utils.AppDatabase;
import nz.co.redice.newsfeeder.utils.pager.Category;


public class ListViewModel extends AndroidViewModel {

    private Category mCategory;
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

    private void searchFor(String keyWord) {
        mDisposable.add(newsService.search(keyWord, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .map(h -> h.getArticles())
                .flatMap(s -> Observable.fromIterable(s))
                .subscribe(s -> s.toEntry()));
    }


    public void gimmeSomeNews() {
        // TODO: 5/4/2020   time intervals for ui update if idle
        // TODO: 5/4/2020   time scope for database cleaning
        clearDatabase();

        switch (mCategory) {
            case TOPS_HEADLINES:
            default:
                getTopNews();
                break;
            case HEALTH:
                getCategory("health");
                break;
            case SPORTS:
                getCategory("sports");
                break;
            case SCIENCE:
                getCategory("science");
                break;
            case BUSINESS:
                getCategory("business");
                break;
            case TECHNOLOGY:
                getCategory("technology");
                break;
            case ENTERTAINMENT:
                getCategory("entertainment");
                break;
        }


    }

    private void getTopNews() {
        mDisposable.add(newsService.requestTopHeadlines(country, apiKey)
                .subscribeOn(Schedulers.io())
                .toObservable()
                .flatMap(s -> Observable.fromIterable(s.getArticles()))
                .map(Article::toEntry)
                .doOnComplete(this::loadFromDatabase)
                .subscribe(s -> db.mEntryDao().insertEntry(s)));
    }

    private void getCategory(String category) {

        mDisposable.add(newsService.requestByCategory(country, apiKey, category)
                .subscribeOn(Schedulers.io())
                .toObservable()
                .flatMap(s -> Observable.fromIterable(s.getArticles()))
                .map(Article::toEntry)
                .doOnComplete(this::loadFromDatabase)
                .subscribe(s -> db.mEntryDao().insertEntry(s)));
    }


    public void loadFromDatabase() {

        // TODO: 5/6/2020 intervals
//        Observable.interval(0, 5, TimeUnit.MINUTES)
//                .debounce(2, TimeUnit.SECONDS)
//                .subscribe(i -> this.loadFromDatabase());


        mDisposable.add((db.mEntryDao().getAllEntries())
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

    public void setCategory(Category category) {
        mCategory = category;
    }
}
