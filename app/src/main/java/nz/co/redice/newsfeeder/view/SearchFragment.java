package nz.co.redice.newsfeeder.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jakewharton.rxbinding3.widget.RxTextView;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nz.co.redice.newsfeeder.databinding.FragmentSearchBinding;
import nz.co.redice.newsfeeder.di.base.MyApplication;
import nz.co.redice.newsfeeder.di.modules.ViewModelFactory;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;
import nz.co.redice.newsfeeder.view.presentation.EntrySelectedListener;
import nz.co.redice.newsfeeder.view.presentation.ListFragmentDirections;
import nz.co.redice.newsfeeder.view.presentation.RecyclerAdapter;
import nz.co.redice.newsfeeder.viewmodel.DetailViewModel;
import nz.co.redice.newsfeeder.viewmodel.SearchViewModel;

public class SearchFragment extends Fragment implements EntrySelectedListener, View.OnClickListener {

    @Inject ViewModelFactory mViewModelFactory;
    private FragmentSearchBinding mBinding;
    private SearchViewModel mViewModel;
    private RecyclerAdapter mRecyclerAdapter;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MyApplication.getAppComponent(context).inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(SearchViewModel.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = setViewBinding(inflater, container);
        setRecyclerView();

        autoSearch();

        mBinding.searchButton.setOnClickListener(this);
        return view;
    }

    @SuppressLint("CheckResult")
    private void autoSearch() {
        RxTextView.textChanges(mBinding.editText)
                .subscribeOn(Schedulers.io())
                .filter(text -> text.length() >= 3)
                .debounce(150, TimeUnit.MILLISECONDS)
                .subscribe(s -> {
                    mViewModel.fetchByKeyword(s.toString());
                    updateSearchResults(s.toString());
                });
    }

    @SuppressLint("CheckResult")
    private void updateSearchResults(String keyword) {
        Observable.just(mViewModel.getEntryList(keyword))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> s.observe(getViewLifecycleOwner(), newList ->
                        mRecyclerAdapter.updateShowList(newList)));
    }


    @Override
    public void onClick(Entry entry) {
        DetailViewModel detailViewModel = new ViewModelProvider(getActivity(), mViewModelFactory).get(DetailViewModel.class);
        detailViewModel.setSelectedRepo(entry);
        ListFragmentDirections.DetailFragment action = ListFragmentDirections.detailFragment();
        action.setCategory("Categories");
        Navigation.findNavController(mBinding.parentLayout).navigate(action);
    }

    @NotNull
    private View setViewBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = this.mBinding.getRoot();
        return view;
    }

    private void setRecyclerView() {
        this.mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerAdapter = new RecyclerAdapter();
        mRecyclerAdapter.setListener(this);
        this.mBinding.recyclerview.setAdapter(mRecyclerAdapter);
    }


    @Override
    public void onClick(View v) {
        autoSearch();
    }
}
