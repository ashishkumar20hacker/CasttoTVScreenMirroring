package com.ide.codekit.casttotv.Adapter;


import static com.ide.codekit.casttotv.Extras.Utils.pushEffectCardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.Model.ObDataModel;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.ViewOnboardScreenBinding;

import java.util.Objects;

public class OnBoardingAdapter extends ListAdapter<ObDataModel, OnBoardingAdapter.ViewHolder> {

    static DiffUtil.ItemCallback<ObDataModel> diffCallback = new DiffUtil.ItemCallback<ObDataModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull ObDataModel oldItem, @NonNull ObDataModel newItem) {
            return Objects.equals(oldItem.getName(), newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ObDataModel oldItem, @NonNull ObDataModel newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

    private OnNextBtnClickListener onNextBtnClickListener;

    public OnBoardingAdapter(OnNextBtnClickListener onNextBtnClickListener) {
        super(diffCallback);
        this.onNextBtnClickListener = onNextBtnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewOnboardScreenBinding binding = ViewOnboardScreenBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObDataModel model = getItem(position);

        holder.binding.dots.setImageResource(model.getIcon());

        holder.binding.title.setText(model.getName());
        Utils.applyGradientOnTv(holder.binding.title,"#43E97B", "#38F9D7");

        holder.binding.desc.setText(model.getValue());

        if (model.getGifImage() == 1) {
            holder.binding.imageView.setImageResource(R.drawable.ob_one);
        } else if (model.getGifImage() == 2) {
            holder.binding.imageView.setImageResource(R.drawable.ob_two);
        } else {
            holder.binding.imageView.setImageResource(R.drawable.ob_three);
        }

        holder.binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushEffectCardView(holder.binding.nextBtn, new Utils.onPushEffect() {
                    @Override
                    public void onClick() {
                        onNextBtnClickListener.onClick();
                    }
                }, false);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ViewOnboardScreenBinding binding;

        public ViewHolder(@NonNull ViewOnboardScreenBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    public interface OnNextBtnClickListener {
        void onClick();
    }

}