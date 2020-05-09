package nz.co.redice.newsfeeder.view.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nz.co.redice.newsfeeder.repository.local.dao.Entry;
import nz.co.redice.newsfeeder.databinding.RecyclerItemBinding;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private final OnEntryClickListener mListener;
    private List<Entry> showList = new ArrayList<>();

    public RecyclerAdapter(OnEntryClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerItemBinding holderBinding = RecyclerItemBinding.inflate(inflater, parent, false);
        return new Holder(holderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(showList.get(position), mListener);
    }

    public void updateShowList(Entry entry) {
        add(entry);
        sortByDate();
    }

    private void sortByDate() {
        Collections.sort(showList, (o1, o2) -> o2.getPublishedAt().compareTo(o1.getPublishedAt()));
        notifyDataSetChanged();
    }

    private void sortBySource() {
        Collections.sort(showList, (o1, o2) -> o1.source.compareTo(o2.source));
        notifyDataSetChanged();
    }



    private void add(Entry newItem) {

        for (Entry stockItem : showList) {
            if (stockItem.title.equals(newItem.title)) {
                return;
            }
        }
        this.showList.add(newItem);
        notifyItemInserted(showList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public void clearList() {
        showList.clear();
    }

    public static class Holder extends RecyclerView.ViewHolder  {


        private RecyclerItemBinding mBinding;

        public Holder(RecyclerItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Entry entry, OnEntryClickListener listener) {
            mBinding.setEntry(entry);
            mBinding.setListener(listener);
        }


    }


}