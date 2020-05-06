package nz.co.redice.newsfeeder.utils.pager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import nz.co.redice.newsfeeder.view.ListFragment;


public class PagerAdapter extends FragmentStatePagerAdapter {
    private List<Category> mTabs = new ArrayList<>();

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void setTabs(List<Category> tabs) {
        mTabs.clear();
        mTabs.addAll(tabs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        ListFragment fragment = new ListFragment();
//        fragment.setCategory(mTabs.get(position));
//        return fragment;
        return ListFragment.newInstance(mTabs.get(position));
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).toString();
    }
}
