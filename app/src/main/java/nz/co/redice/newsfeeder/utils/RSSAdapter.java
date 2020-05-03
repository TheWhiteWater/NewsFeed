package nz.co.redice.newsfeeder.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nz.co.redice.newsfeeder.dao.Entry;


public class RSSAdapter extends RecyclerView.Adapter<RSSAdapter.Holder> {

    private List<Entry> showList = new ArrayList<>();


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        nz.co.redice.newsfeeder.databinding.DataItemBinding holderBinding = nz.co.redice.newsfeeder.databinding.DataItemBinding.inflate(inflater, parent, false);
        return new Holder(holderBinding);

//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.data_item, parent, false);
//        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(showList.get(position));
    }

    public void updateShowList(Entry entry) {
        add(entry);
        sortByDate();
    }

    public void clearShowList() {
        showList.clear();
    }

    private void add(Entry update) {
        for (Entry stock : showList) {
            if (stock.equals(update)) {
                return;
            }
        }
        this.showList.add(update);
        notifyItemInserted(showList.size() - 1);
    }

    private void sortByDate() {
        Collections.sort(showList, (o1, o2) -> o2.getPublishedAt().compareTo(o1.getPublishedAt()));
        notifyDataSetChanged();
    }

    private void sortBySource() {
        Collections.sort(showList, (o1, o2) -> o1.source.compareTo(o2.source));
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return showList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {


        private nz.co.redice.newsfeeder.databinding.DataItemBinding mBinding;

        public Holder(nz.co.redice.newsfeeder.databinding.DataItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

        }

        public void bind(Entry entry) {
            mBinding.setEntry(entry);
        }
    }
}