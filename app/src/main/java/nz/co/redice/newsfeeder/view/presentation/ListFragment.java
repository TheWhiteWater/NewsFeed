package nz.co.redice.newsfeeder.view.presentation;

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

import nz.co.redice.newsfeeder.databinding.FragmentListBinding;
import nz.co.redice.newsfeeder.viewmodel.ListViewModel;


public class ListFragment extends Fragment implements OnEntryClickListener {

    private FragmentListBinding mBinding;
    private ListViewModel mViewModel;
    private RecyclerAdapter mRecyclerAdapter;
    private Category mCategory;


    public static ListFragment newInstance(Category category) {
        ListFragment fragment = new ListFragment(category);
        return fragment;
    }


    public ListFragment(Category category) {
        mCategory = category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentListBinding.inflate(inflater, container, false);
        View view = this.mBinding.getRoot();

        this.mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerAdapter = new RecyclerAdapter(this);
        this.mBinding.recyclerview.setAdapter(mRecyclerAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel.fetchCategory(mCategory.getTag());

        mViewModel.getEntry().observe(getViewLifecycleOwner(), showEntry -> {
            mRecyclerAdapter.updateShowList(showEntry);
            this.mBinding.errorTextView.setVisibility(View.INVISIBLE);
            this.mBinding.progressbar.setVisibility(View.INVISIBLE);
        });
        mViewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {
            this.mBinding.progressbar.setVisibility(loading ? View.VISIBLE : View.GONE);
            this.mBinding.errorTextView.setVisibility(loading ? View.GONE : View.INVISIBLE);
            this.mBinding.recyclerview.setVisibility(loading ? View.GONE : View.VISIBLE);
        });
        mViewModel.getError().observe(getViewLifecycleOwner(),
                error -> mBinding.errorTextView.setVisibility(error ? View.VISIBLE : View.GONE));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }


    @Override
    public void onClick(int uuid, String category) {
        ListFragmentDirections.DetailFragment action = ListFragmentDirections.detailFragment();
        action.setUuid(uuid);
        action.setCategory(category);
        Navigation.findNavController(mBinding.refreshLayout).navigate(action);

    }

    public void onRefresh() {
        mViewModel.clearDatabase();
        mRecyclerAdapter.clearList();
        mViewModel.fetchCategory(mCategory.getTag());
        mBinding.refreshLayout.setRefreshing(false);
    }
}
