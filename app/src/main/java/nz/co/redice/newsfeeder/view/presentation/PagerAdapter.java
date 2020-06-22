package nz.co.redice.newsfeeder.view.presentation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import nz.co.redice.newsfeeder.repository.utils.Constants;


public class PagerAdapter extends FragmentStateAdapter {

    private List<Category> mTabs = new ArrayList<>();

    public PagerAdapter(@NonNull Fragment fragment, List<Category> list) {
        super(fragment);
        mTabs.clear();
        mTabs.addAll(list);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ListFragment.newInstance(mTabs.get(position));
    }

    @Override
    public int getItemCount() {
        return mTabs.size();
    }

}
