package nz.co.redice.newsfeeder.view.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import nz.co.redice.newsfeeder.databinding.RecyclerItemBinding;
import nz.co.redice.newsfeeder.repository.local.dao.Entry;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private List<Entry> showList = new ArrayList<>();
    private EntrySelectedListener mListener;

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

    public void updateShowList(List<Entry> list) {
        if (showList.size() > 0) {
            showList.clear();
        }
        for (Entry e : list) {
            add(e);
        }
        sortByDate();
        notifyDataSetChanged();
    }

    private void sortByDate() {
        Collections.sort(showList, (o1, o2) -> o2.getPublishedAt().compareTo(o1.getPublishedAt()));
        notifyDataSetChanged();
    }


    private void add(Entry newItem) {
        Observable.just(showList)
                .flatMap(Observable::fromIterable)
                .filter(s -> s.equals(newItem))
                .toList()
                .subscribe(s -> {
                    if (s.size() < 1) {
                        showList.add(newItem);
//                        notifyItemInserted(showList.size() - 1);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public void setListener(EntrySelectedListener listener) {
        mListener = listener;
    }

    public void clearList() {
        showList.clear();
        notifyDataSetChanged();
    }


    public static class Holder extends RecyclerView.ViewHolder {

        private RecyclerItemBinding mBinding;

        public Holder(RecyclerItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Entry entry, EntrySelectedListener onClickListener) {
            mBinding.setEntry(entry);
            mBinding.itemParentLayout.setOnClickListener(v -> {
                if (entry != null)
                    onClickListener.onClick(entry);
            });
        }


    }
}