package nz.co.redice.newsfeeder.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Arrays;

import nz.co.redice.newsfeeder.databinding.FragmentHomeBinding;
import nz.co.redice.newsfeeder.utils.pager.Category;
import nz.co.redice.newsfeeder.utils.pager.PagerAdapter;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;
    private PagerAdapter mPagerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPagerAdapter = new PagerAdapter(getChildFragmentManager());
        mBinding.viewpager.setAdapter(mPagerAdapter);

        mBinding.tablayout.setupWithViewPager(mBinding.viewpager);

        mPagerAdapter.setTabs(Arrays.asList(
                Category.TOPS_HEADLINES,
                Category.BUSINESS,
                Category.ENTERTAINMENT,
                Category.HEALTH,
                Category.SCIENCE,
                Category.SPORTS,
                Category.TECHNOLOGY
        ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
