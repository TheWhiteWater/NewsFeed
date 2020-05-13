package nz.co.redice.newsfeeder.view.presentation;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import javax.inject.Inject;

import nz.co.redice.newsfeeder.R;
import nz.co.redice.newsfeeder.databinding.FragmentListBinding;
import nz.co.redice.newsfeeder.di.modules.ViewModelFactory;
import nz.co.redice.newsfeeder.di.base.MyApplication;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;
import nz.co.redice.newsfeeder.viewmodel.DetailViewModel;
import nz.co.redice.newsfeeder.viewmodel.ListViewModel;


public class ListFragment extends Fragment implements EntrySelectedListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject ViewModelFactory mViewModelFactory;
    private FragmentListBinding mBinding;
    private ListViewModel mViewModel;
    private RecyclerAdapter mRecyclerAdapter;
    private Category mCategory;


    public static ListFragment newInstance(Category category) {
        ListFragment fragment = new ListFragment(category);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MyApplication.getAppComponent(context).inject(this);
    }

    public ListFragment(Category category) {
        mCategory = category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(ListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentListBinding.inflate(inflater, container, false);
        View view = this.mBinding.getRoot();

        this.mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerAdapter = new RecyclerAdapter();
        mRecyclerAdapter.setListener(this);
        this.mBinding.recyclerview.setAdapter(mRecyclerAdapter);

        mBinding.refreshLayout.setOnRefreshListener(this);
        mBinding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.fetchCategory(mCategory.getTag());
        getCategoryList(mCategory.getTag());

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }


    @Override
    public void onRefresh() {
        mRecyclerAdapter.clearList();
        getCategoryList(mCategory.getTag());
        mBinding.refreshLayout.setRefreshing(false);
    }

    private void getCategoryList(String category) {
        mViewModel.getEntryList(category).observe(getViewLifecycleOwner(), newList -> {
            mRecyclerAdapter.updateShowList(newList);
        });
    }

    @Override
    public void onClick(Entry entry) {
        DetailViewModel detailViewModel = new ViewModelProvider(getActivity(), mViewModelFactory).get(DetailViewModel.class);
        detailViewModel.setSelectedRepo(entry);
        ListFragmentDirections.DetailFragment action = ListFragmentDirections.detailFragment();
        action.setCategory(mCategory.toString());
        Navigation.findNavController(mBinding.refreshLayout).navigate(action);
    }
}
