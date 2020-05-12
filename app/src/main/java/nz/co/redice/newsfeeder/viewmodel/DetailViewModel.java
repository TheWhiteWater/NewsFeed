package nz.co.redice.newsfeeder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.disposables.CompositeDisposable;
import nz.co.redice.newsfeeder.repository.Repository;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;

public class DetailViewModel extends AndroidViewModel {


    private Repository mRepository;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(getApplication());
    }

    public LiveData<Entry> getEntryById(int uuid) {
        return mRepository.retrieveEntryById(uuid);
    }



}
