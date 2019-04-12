package com.allen.pad.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.allen.pad.Model.Image;
import com.allen.pad.R;
import com.bumptech.glide.Glide;

public class ImageAdapter extends ListAdapter<Image, ImageAdapter.ViewHolderImage> {
    private onRemoveClickListener listener;
    private onItemClickListener clickListener;
    private Context context;

    public ImageAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Image> DIFF_CALLBACK = new DiffUtil.ItemCallback<Image>() {
        @Override
        public boolean areItemsTheSame(@NonNull Image image, @NonNull Image t1) {
            return image.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Image image, @NonNull Image t1) {
            return image.getTitle().equals(t1.getTitle());
        }
    };


    @NonNull
    @Override
    public ViewHolderImage onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, viewGroup, false);
        return new ViewHolderImage(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderImage viewHolderImage, int i) {
        Image image = getItem(i);
        Glide
                .with(context)
                .load(image.getTitle())
                .into(viewHolderImage.iv_image);

    }

    public Image getImageAt(int adapterPosition) {
        return getItem(adapterPosition);
    }

    class ViewHolderImage extends RecyclerView.ViewHolder {
        private ImageView iv_image;
        private ImageView iv_delete;

        public ViewHolderImage(@NonNull View itemView) {
            super(itemView);

            iv_image = itemView.findViewById(R.id.iv_image);
            iv_delete = itemView.findViewById(R.id.iv_delete);

            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                        clickListener.onItemClick(getItem(getAdapterPosition()));
                }
            });

            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                        listener.onRemoveClick(getItem(getAdapterPosition()));
                }
            });
        }
    }

    public interface onRemoveClickListener{
        void onRemoveClick(Image image);
    }

    public void setOnItemRemoveListener(onRemoveClickListener listener){
        this.listener = listener;
    }

    public interface onItemClickListener{
        void onItemClick(Image image);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.clickListener = listener;
    }

}
