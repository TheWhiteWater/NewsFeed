package nz.co.redice.newsfeeder.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import nz.co.redice.newsfeeder.dao.Entry;
import nz.co.redice.newsfeeder.utils.AppDatabase;

public class DetailViewModel extends AndroidViewModel {

    private MutableLiveData<Entry> entry = new MutableLiveData<>();
    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();


    private AppDatabase db = Room.databaseBuilder(getApplication(),
            AppDatabase.class, "news_api.db").build();
    private final CompositeDisposable mDisposable = new CompositeDisposable();


    public MutableLiveData<Entry> getEntry() {
        return entry;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }


    public void loadEntryFromDatabase(int uuid) {
        mDisposable.add((db.mEntryDao().getEntry(uuid))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> setLifeData(s)));
    }


    private void setLifeData(Entry s) {
        entry.setValue(s);
        error.setValue(false);
        loading.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.dispose();
    }

}
