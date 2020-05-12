package nz.co.redice.newsfeeder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import nz.co.redice.newsfeeder.repository.Repository;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;


public class ListViewModel extends AndroidViewModel {

    private final Repository mRepository;

    public ListViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(getApplication());
    }

    public void fetchCategory(String category) {
        Observable.interval(0, 5, TimeUnit.MINUTES)
                .subscribeOn(Schedulers.io())
                .subscribe(s -> mRepository.requestCategory(category));
    }

    public LiveData<List<Entry>> getEntryList(String category) {
        return mRepository.retrieveByCategory(category);
    }


}
