package nz.co.redice.newsfeeder.view.presentation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;


public class PagerAdapter extends FragmentStateAdapter {

    List<Category> mTabs = new ArrayList<>();

    public PagerAdapter(@NonNull Fragment fragment, List <Category> list) {
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
