package nz.co.redice.newsfeeder.view;

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

import nz.co.redice.newsfeeder.databinding.FragmentDetailBinding;
import nz.co.redice.newsfeeder.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding mBinding;
    private DetailViewModel mViewModel;
    private int uuid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(mBinding.toolbar, navController, appBarConfiguration);
        uuid = DetailFragmentArgs.fromBundle(getArguments()).getUuid();


        mViewModel.loadEntryFromDatabase(uuid);

        mViewModel.getEntry().observe(getViewLifecycleOwner(),
                s -> mBinding.setEntry(s));
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
