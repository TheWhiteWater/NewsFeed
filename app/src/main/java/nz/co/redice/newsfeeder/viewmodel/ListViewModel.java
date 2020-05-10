package nz.co.redice.newsfeeder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import nz.co.redice.newsfeeder.repository.Repository;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;


public class ListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Entry>> mEntryList = new MutableLiveData<>();
    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private final Repository mRepository;

    public ListViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(getApplication());

    }

    public LiveData<List<Entry>> getEntryList(String category) {
        mRepository.requestCategory(category);
        return mRepository.retrieveByCategory(category);
    }

//    public MutableLiveData<Boolean> getError() {
//        return error;
//    }
//
//    public MutableLiveData<Boolean> getLoading() {
//        return loading;
//    }


    // TODO: 5/4/2020   time intervals for ui update if idle
    // TODO: 5/4/2020   time scope for database cleaning
    public void fetchCategory(String category) {
        mRepository.clearDatabase();
        mRepository.requestCategory(category);
    }

    public void clearDatabase() {
        mRepository.clearDatabase();
    }


}
