package com.ide.codekit.casttotv.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.ide.codekit.casttotv.Extras.ImageDialog;
import com.ide.codekit.casttotv.databinding.ItemOnlineImagesBinding;

import java.util.Objects;

public class OnlineImagesAdapter extends ListAdapter<String, OnlineImagesAdapter.ViewHolder> {

    static DiffUtil.ItemCallback<String> diffCallback = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return Objects.equals(oldItem, newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

    public OnlineImagesAdapter() {
        super(diffCallback);
    }
    private Context context;

    @NonNull
    @Override
    public OnlineImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ItemOnlineImagesBinding binding = ItemOnlineImagesBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new OnlineImagesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineImagesAdapter.ViewHolder holder, int position) {
        String url = getItem(position);
        Glide.with(context).load(url).into(holder.binding.resultIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageDialog dialog = new ImageDialog(context, url);
                dialog.show();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemOnlineImagesBinding binding;
        public ViewHolder(@NonNull ItemOnlineImagesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
