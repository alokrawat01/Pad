package com.allen.pad.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.allen.pad.Model.Label;
import com.allen.pad.R;

public class LabelAddAdapter extends ListAdapter<Label, LabelAddAdapter.ViewHolderNote> {
    private onItemClickListener listener;

    public LabelAddAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Label> DIFF_CALLBACK = new DiffUtil.ItemCallback<Label>() {
        @Override
        public boolean areItemsTheSame(@NonNull Label note, @NonNull Label t1) {
            return note.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Label note, @NonNull Label t1) {
            return note.getTitle().equals(t1.getTitle());
        }
    };

    @NonNull
    @Override
    public ViewHolderNote onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_label_add, viewGroup, false);
        return new ViewHolderNote(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNote viewHolderNote, int i) {
        String string = getItem(i).getTitle();
        viewHolderNote.tv_label.setText(string);
    }

    public Label getNoteAt(int adapterPosition) {
        return getItem(adapterPosition);
    }

    class ViewHolderNote extends RecyclerView.ViewHolder {
        private TextView tv_label;

        public ViewHolderNote(@NonNull View itemView) {
            super(itemView);
            tv_label = itemView.findViewById(R.id.tv_label);

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
        void onItemClick(Label note);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
