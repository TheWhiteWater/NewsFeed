package nz.co.redice.newsfeeder.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.AppBarLayout;

import javax.inject.Inject;

import nz.co.redice.newsfeeder.databinding.FragmentDetailBinding;
import nz.co.redice.newsfeeder.repository.di.ViewModelFactory;
import nz.co.redice.newsfeeder.repository.di.base.MyApplication;
import nz.co.redice.newsfeeder.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {

    @Inject ViewModelFactory mViewModelFactory;
    private FragmentDetailBinding mBinding;
    private DetailViewModel mViewModel;
    private String mCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MyApplication.getAppComponent(context).inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity(), mViewModelFactory).get(DetailViewModel.class);
        mViewModel.restoreEntry(savedInstanceState);

        setToolbar(view);
        displayScreen();
        setCollapsingTitle();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewModel.saveToBundle(outState);
    }

    private void setCollapsingTitle() {
        mBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mBinding.collapsingToolbar.setTitle(mCategory);
                    isShow = true;
                } else if (isShow) {
                    mBinding.collapsingToolbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }

    private void setToolbar(@NonNull View view) {
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(mBinding.toolbar, navController, appBarConfiguration);
    }

    private void displayScreen() {
        mCategory = DetailFragmentArgs.fromBundle(getArguments()).getCategory();
        mViewModel.getSelectedEntry().observe(getViewLifecycleOwner(),
                s -> mBinding.setEntry(s));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
