package com.ide.codekit.casttotv.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ide.codekit.casttotv.Model.DataModel;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.ItemMediaBinding;

import java.util.Objects;

public class CastAdapter extends ListAdapter<DataModel, CastAdapter.ViewHolder> {

    static DiffUtil.ItemCallback<DataModel> diffCallback = new DiffUtil.ItemCallback<DataModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull DataModel oldItem, @NonNull DataModel newItem) {
            return Objects.equals(oldItem, newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull DataModel oldItem, @NonNull DataModel newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

    public CastAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMediaBinding binding = ItemMediaBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.ViewHolder holder, int position) {
        DataModel model = getItem(position);

        holder.binding.previewIv.setImageResource(R.drawable.cast);
        holder.binding.name.setText(model.getServiceName());
        holder.binding.size.setText(model.getHostAddress());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMediaBinding binding;
        public ViewHolder(@NonNull ItemMediaBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
