package nz.co.redice.newsfeeder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import nz.co.redice.newsfeeder.repository.Repository;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;

public class SearchViewModel extends ViewModel {

    private Repository mRepository;

    @Inject
    public SearchViewModel(Repository repository) {
        mRepository = repository;
    }

    public void fetchByKeyword(String keyword) {
        mRepository.requestByKeyword(keyword);
    }

    public LiveData<List<Entry>> getEntryList(String keyword) {
        return mRepository.retrieveByCategory(keyword);
    }

}
