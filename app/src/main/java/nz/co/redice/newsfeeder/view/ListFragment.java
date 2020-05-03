package nz.co.redice.newsfeeder.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import nz.co.redice.newsfeeder.databinding.FragmentListBinding;
import nz.co.redice.newsfeeder.utils.RSSAdapter;
import nz.co.redice.newsfeeder.viewmodel.ListViewModel;


public class ListFragment extends Fragment {

    private FragmentListBinding mBinding;
    private RSSAdapter mRSSAdapter;
    private RecyclerView mRecyclerView;
    private TextView mErrorTextView;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentListBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        mSwipeRefreshLayout = mBinding.refreshLayout;
        mRecyclerView = mBinding.recyclerview;
        mErrorTextView = mBinding.errorTextView;
        mProgressBar = mBinding.progressbar;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRSSAdapter = new RSSAdapter();
        mRecyclerView.setAdapter(mRSSAdapter);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        viewModel.gimmeSomeAction();
//        viewModel.updateLiveData();

        viewModel.getHeadlines().observe(getViewLifecycleOwner(), showEntry -> {
            mRSSAdapter.updateShowList(showEntry);
            mErrorTextView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        });
        viewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {
            mProgressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
            mErrorTextView.setVisibility(loading ? View.GONE : View.VISIBLE);
            mRecyclerView.setVisibility(loading ? View.GONE : View.VISIBLE);
        });
        viewModel.getError().observe(getViewLifecycleOwner(),
                error -> mErrorTextView.setVisibility(error ? View.VISIBLE : View.GONE));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
