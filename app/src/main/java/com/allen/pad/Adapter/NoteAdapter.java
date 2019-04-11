package com.allen.pad.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allen.pad.Model.Note;
import com.allen.pad.R;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.ViewHolderNote> {
    private onItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getTitle().equals(t1.getTitle()) &&
                    note.getDescription().equals(t1.getDescription()) &&
                    note.getPriority() == t1.getPriority();
        }
    };


    @NonNull
    @Override
    public ViewHolderNote onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note, viewGroup, false);
        return new ViewHolderNote(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNote viewHolderNote, int i) {
        Note note = getItem(i);
        viewHolderNote.tv_title.setText(note.getTitle());
        viewHolderNote.tv_priority.setText(String.valueOf(note.getPriority()));
        viewHolderNote.tv_description.setText(note.getDescription());
    }

    public Note getNoteAt(int adapterPosition) {
        return getItem(adapterPosition);
    }

    class ViewHolderNote extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_priority;
        private TextView tv_description;

        public ViewHolderNote(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_priority = itemView.findViewById(R.id.tv_priorty);
            tv_description = itemView.findViewById(R.id.tv_description);

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
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
