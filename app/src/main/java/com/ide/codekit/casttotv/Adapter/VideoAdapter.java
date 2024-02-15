package com.ide.codekit.casttotv.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ide.codekit.casttotv.Model.DataModel;
import com.ide.codekit.casttotv.databinding.ItemAudioBinding;
import com.ide.codekit.casttotv.databinding.ItemMediaBinding;
import com.ide.codekit.casttotv.databinding.ItemVideoPlayerBinding;

import java.util.Objects;

public class VideoAdapter extends ListAdapter<DataModel, RecyclerView.ViewHolder> {

    public static final int VIEW_GRID = 1;
    public static final int VIEW_LINEAR = 2;
    public static final int VIEW_AUDIO = 3;

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

    private Context context;
    private DataClickListener dataClickListener;


    private int currentViewType = VIEW_GRID;

    public void setViewType(int viewType) {
        currentViewType = viewType;
        notifyDataSetChanged();  // Notify adapter that data set has changed
    }

    public VideoAdapter(DataClickListener dataClickListener) {
        super(diffCallback);
        this.dataClickListener = dataClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_GRID:
                ItemVideoPlayerBinding animationBinding =
                        ItemVideoPlayerBinding.inflate(inflater, parent, false);
                return new ViewHolderGrid(animationBinding);
            case VIEW_LINEAR:
                ItemMediaBinding chargingBinding =
                        ItemMediaBinding.inflate(inflater, parent, false);
                return new ViewHolderLinear(chargingBinding);
            case VIEW_AUDIO:
                ItemAudioBinding audioBinding =
                        ItemAudioBinding.inflate(inflater, parent, false);
                return new ViewHolderAudio(audioBinding);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataModel dataModel = getItem(position);

        switch (holder.getItemViewType()) {
            case VIEW_GRID:
                ViewHolderGrid animationViewHolder = (ViewHolderGrid) holder;
                bindCommonData(animationViewHolder, dataModel);
                // Additional logic specific to VIEW_TYPE_ANIMATION
                break;
            case VIEW_LINEAR:
                ViewHolderLinear chargingViewHolder = (ViewHolderLinear) holder;
                bindCommonData(chargingViewHolder, dataModel);
                // Additional logic specific to VIEW_TYPE_CHARGING
                break;
            case VIEW_AUDIO:
                ViewHolderAudio audioViewHolder = (ViewHolderAudio) holder;
                bindCommonData(audioViewHolder, dataModel);
                // Additional logic specific to VIEW_TYPE_CHARGING
                break;
        }

        holder.itemView.setOnClickListener(v -> dataClickListener.onDataClick(dataModel));
    }

    @Override
    public int getItemViewType(int position) {
        // Implement your logic to determine the view type for each position
        // You may want to use constants like VIEW_TYPE_ANIMATION, VIEW_TYPE_CHARGING, etc.
        return currentViewType;
    }

    private void bindCommonData(ViewHolderCommon holder, DataModel dataModel) {
        if (dataModel != null) {
            holder.loadImage(dataModel);
//            holder.setupFavoriteButton(url);
        }
    }

    abstract static class ViewHolderCommon extends RecyclerView.ViewHolder {


        public ViewHolderCommon(View itemView) {
            super(itemView);

        }

        abstract void loadImage(DataModel dataModel);

//        abstract void setupFavoriteButton(String url);
    }

    class ViewHolderGrid extends ViewHolderCommon {
        ImageView imageView;

        public ViewHolderGrid(ItemVideoPlayerBinding binding) {
            super(binding.getRoot());
            imageView = binding.preview;
        }

        @Override
        void loadImage(DataModel dataModel) {
            try {
                Glide.with(context).load(dataModel.getPath()).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    class ViewHolderLinear extends ViewHolderCommon {
        ImageView imageView,sharebt;
        TextView title,size;

        public ViewHolderLinear(ItemMediaBinding binding) {
            super(binding.getRoot());
            imageView = binding.previewIv;
            sharebt = binding.share;
            title = binding.name;
            size = binding.size;
        }

        @Override
        void loadImage(DataModel dataModel) {
            try {
                Glide.with(context).load(dataModel.getPath()).into(imageView);
                title.setText(dataModel.getName());
                size.setText(dataModel.getSize());
            } catch (Exception e) {
                e.printStackTrace();
            }

            sharebt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataClickListener.onShare(dataModel);
                }
            });
        }

    }
    class ViewHolderAudio extends ViewHolderCommon {
        ImageView imageView,sharebt;
        TextView title,size;

        public ViewHolderAudio(ItemAudioBinding binding) {
            super(binding.getRoot());
            imageView = binding.previewIv;
            sharebt = binding.share;
            title = binding.name;
            size = binding.size;
        }

        @Override
        void loadImage(DataModel dataModel) {
            try {
                title.setText(dataModel.getName());
                size.setText(dataModel.getSize());
            } catch (Exception e) {
                e.printStackTrace();
            }

            sharebt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataClickListener.onShare(dataModel);
                }
            });
        }

    }

    public interface DataClickListener {
        void onDataClick(DataModel dataModel);

        void onShare(DataModel model);
    }
}
