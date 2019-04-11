package com.allen.pad.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.allen.pad.Model.Color;
import com.allen.pad.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolderColor> {
    private List<Color> lisColor;
    private Context context;
    private onItemClickListener listener;

    public ColorAdapter(List<Color> lisColor, Context context) {
        this.lisColor = lisColor;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderColor onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_color, viewGroup, false);
        return new ViewHolderColor(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderColor viewHolderColor, int i) {
        viewHolderColor.iv_color.setBackgroundColor(lisColor.get(i).getColor());
        if (lisColor.get(i).isSelected()){
            viewHolderColor.iv_color_selected.setVisibility(View.VISIBLE);
        }else {
            viewHolderColor.iv_color_selected.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return lisColor.size();
    }

    class ViewHolderColor extends RecyclerView.ViewHolder{
        ImageView iv_color;
        ImageView iv_color_selected;

        public ViewHolderColor(@NonNull View itemView) {
            super(itemView);
            iv_color = itemView.findViewById(R.id.iv_color);
            iv_color_selected = itemView.findViewById(R.id.iv_color_selected);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                        listener.onItemClick(lisColor.get(getAdapterPosition()));
                    for (int i = 0; i <lisColor.size() ; i++) {
                        if (i == getAdapterPosition()){
                            lisColor.get(i).setSelected(true);
                        }else {
                            lisColor.get(i).setSelected(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(Color color);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
