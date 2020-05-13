package nz.co.redice.newsfeeder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import nz.co.redice.newsfeeder.repository.Repository;
import nz.co.redice.newsfeeder.repository.di.ViewModelFactory;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;


public class ListViewModel extends ViewModel {

    private Repository mRepository;

    @Inject
    public ListViewModel(Repository repository) {
        mRepository = repository;
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
