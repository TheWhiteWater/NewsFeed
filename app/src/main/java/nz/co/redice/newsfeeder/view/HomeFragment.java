package nz.co.redice.newsfeeder.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import nz.co.redice.newsfeeder.databinding.FragmentHomeBinding;
import nz.co.redice.newsfeeder.repository.utils.Constants;
import nz.co.redice.newsfeeder.view.presentation.PagerAdapter;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding mBinding;
    private PagerAdapter mPagerAdapter;
    private ViewPager2 mViewPager;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        mBinding.fab.setOnClickListener(this);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPagerAdapter = new PagerAdapter(this, Constants.CATEGORIES);
        mViewPager = mBinding.viewpager;
        mViewPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = mBinding.tablayout;
        new TabLayoutMediator(tabLayout, mViewPager,
                ((tab, position) -> tab.setText(Constants.CATEGORIES.get(position).toString()))).attach();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        NavDirections action = HomeFragmentDirections.homeFragmentToSearchFragment();
        Navigation.findNavController(v).navigate(action);
    }
}
