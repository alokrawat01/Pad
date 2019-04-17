package com.allen.pad.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.allen.pad.Model.Label;
import com.allen.pad.R;

public class LabelAdapter extends ListAdapter<Label, LabelAdapter.ViewHolderNote> {
    onCheckboxEventListener listener;

    public LabelAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Label> DIFF_CALLBACK = new DiffUtil.ItemCallback<Label>() {

        @Override
        public boolean areItemsTheSame(@NonNull Label label, @NonNull Label t1) {
            return label.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Label label, @NonNull Label t1) {

            return label.getTitle().equals(t1.getTitle());
        }
    };

    @NonNull
    @Override
    public ViewHolderNote onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_label, viewGroup, false);
        return new ViewHolderNote(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNote viewHolderNote, final int i) {
        final Label label = getItem(i);
        viewHolderNote.tv_label.setText(label.getTitle());
        viewHolderNote.cb_label.setChecked(label.isChecked());

        viewHolderNote.cb_label.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                label.setChecked(isChecked);
                listener.checkboxEvent(label);
            }
        }
        );

    }

    class ViewHolderNote extends RecyclerView.ViewHolder {
        private TextView tv_label;
        private CheckBox cb_label;


        public ViewHolderNote(@NonNull View itemView) {
            super(itemView);

            tv_label = itemView.findViewById(R.id.tv_label);
            cb_label = itemView.findViewById(R.id.cb_label);

        }
    }

    public interface onCheckboxEventListener {
        void checkboxEvent(Label labels);
    }

    public void setOnCheckChangeListener(onCheckboxEventListener listener){
        this.listener = listener;
    }

}
