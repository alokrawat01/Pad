package com.allen.pad.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import com.allen.pad.Model.Tickbox;
import com.allen.pad.R;

public class TickboxAdapter extends ListAdapter<Tickbox, TickboxAdapter.ViewHolderTickbox> {
    private onItemClickListener listener;

    public TickboxAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Tickbox> DIFF_CALLBACK = new DiffUtil.ItemCallback<Tickbox>() {
        @Override
        public boolean areItemsTheSame(@NonNull Tickbox tickbox, @NonNull Tickbox t1) {
            return tickbox.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Tickbox tickbox, @NonNull Tickbox t1) {
            return tickbox.getTitle().equals(t1.getTitle());
        }
    };


    @NonNull
    @Override
    public ViewHolderTickbox onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tickbox, viewGroup, false);
        return new ViewHolderTickbox(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTickbox viewHolderTickbox, int i) {
        Tickbox tickbox = getItem(i);
        viewHolderTickbox.et_title.setText(tickbox.getTitle());
        viewHolderTickbox.cb.setChecked(tickbox.isChecked());
    }

    public Tickbox getTickboxAt(int adapterPosition) {
        return getItem(adapterPosition);
    }

    class ViewHolderTickbox extends RecyclerView.ViewHolder {
        private EditText et_title;
        private CheckBox cb;

        public ViewHolderTickbox(@NonNull View itemView) {
            super(itemView);

            et_title = itemView.findViewById(R.id.et_title);
            cb = itemView.findViewById(R.id.cb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(getAdapterPosition()));
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(Tickbox tickbox);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
