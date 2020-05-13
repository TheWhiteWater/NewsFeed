package nz.co.redice.newsfeeder.viewmodel;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import nz.co.redice.newsfeeder.repository.Repository;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;

public class DetailViewModel extends ViewModel {


    private final MutableLiveData<Entry> selectedEntry = new MutableLiveData<>();



    private Repository mRepository;

    public LiveData<Entry> getSelectedEntry() {
        return selectedEntry;
    }

    public void setSelectedRepo(Entry entry) {
        selectedEntry.setValue(entry);
    }


    @Inject
    public DetailViewModel(Repository repository) {
        mRepository = repository;
    }

    public void restoreEntry(Bundle savedInstanceState) {
        if (selectedEntry.getValue() == null) {
            int uuid = savedInstanceState.getInt("entry_uuid");
            setSelectedRepo(mRepository.retrieveEntryById(uuid));
        }
    }

    public void saveToBundle(Bundle outState) {
        if (selectedEntry != null) {
            outState.putInt("entry_uuid", selectedEntry.getValue().getUuid());
        }

    }
}
