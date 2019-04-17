package com.allen.pad.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import com.allen.pad.Model.Tickbox;
import com.allen.pad.R;
import java.util.Timer;
import java.util.TimerTask;

public class TickboxAdapter extends ListAdapter<Tickbox, TickboxAdapter.ViewHolderTickbox> {
    private onRemoveClickListener listener;
    private onTextUpdateListener textUpdateListener;

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

    class ViewHolderTickbox extends RecyclerView.ViewHolder {
        private EditText et_title;
        private CheckBox cb;
        private Timer timer;

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

            et_title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            String string = s.toString().trim();
                            getItem(getAdapterPosition()).setTitle(string);
                            if (textUpdateListener != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                                textUpdateListener.onItemClick(getItem(getAdapterPosition()));
                        }
                    }, 1000);
                }
            });
        }
    }

    public interface onRemoveClickListener{
        void onItemClick(Tickbox tickbox);
    }

    public void setOnItemRemoveListener(onRemoveClickListener listener){
        this.listener = listener;
    }

    public interface onTextUpdateListener{
        void onItemClick(Tickbox tickbox);
    }

    public void setOnTextUpdateListener(onTextUpdateListener listener){
        this.textUpdateListener = listener;
    }
}
