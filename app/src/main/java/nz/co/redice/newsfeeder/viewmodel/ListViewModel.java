package nz.co.redice.newsfeeder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import nz.co.redice.newsfeeder.repository.Repository;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;


public class ListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Entry>> mEntryList = new MutableLiveData<>();

    private final Repository mRepository;

    public ListViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance(getApplication());

    }

    public MutableLiveData<List<Entry>> getEntryList() {
        return mEntryList;
    }

    public void fetchCategory(String category) {
        mRepository.retrieveByCategory(category)
                .subscribeOn(Schedulers.io())
                .repeatWhen(completed -> completed.delay(5, TimeUnit.MINUTES))
                .subscribe(x -> mEntryList.postValue(x));
    }


}
