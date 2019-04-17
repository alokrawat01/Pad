package com.allen.pad.AddNote;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.allen.pad.Adapter.LabelAdapter;
import com.allen.pad.Model.Label;
import com.allen.pad.Model.LabelNote;
import com.allen.pad.Model.LabelTemp;
import com.allen.pad.R;
import com.allen.pad.ViewModel.LabelNoteViewModel;
import com.allen.pad.ViewModel.LabelTempViewModel;
import com.allen.pad.ViewModel.LabelViewModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class LabelFragment extends Fragment implements View.OnClickListener {

    ImageView iv_back;
    EditText et_label;
    LinearLayout ll_add_label;
    TextView tv_label;
    RecyclerView rv_label;

    private Timer timer;
    private LabelAdapter adapter;

    LabelViewModel labelViewModel;
    private ArrayList<Label> listLabel;
    private ArrayList<String> listLabelTitle;

    LabelTempViewModel labelTempViewModel;
    private ArrayList<LabelTemp> listTempLabels;

    LabelNoteViewModel labelNoteViewModel;
    ArrayList<LabelNote> listLabelNote;

    int note_id;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_label, container, false);

        if (getArguments() != null) {
            note_id = getArguments().getInt("NOTE_ID", -1);
        }

        labelNoteViewModel = ViewModelProviders.of(this).get(LabelNoteViewModel.class);
        labelNoteViewModel.getAllLabels().observe(this, new Observer<List<LabelNote>>() {
            @Override
            public void onChanged(@Nullable List<LabelNote> labelNotes) {
                listLabelNote = new ArrayList<>(labelNotes);
            }
        });


        labelViewModel = ViewModelProviders.of(this).get(LabelViewModel.class);
        labelViewModel.getAllLabels().observe(this, new Observer<List<Label>>() {
            @Override
            public void onChanged(@Nullable List<Label> labels) {
                listLabel = new ArrayList<>(labels);
                adapter.submitList(listLabel);
                listLabelTitle = new ArrayList<>();

                for (Label l: listLabel) {
                    listLabelTitle.add(l.getTitle());
                }
            }
        });

        labelTempViewModel = ViewModelProviders.of(this).get(LabelTempViewModel.class);
        labelTempViewModel.getAllLabels().observe(this, new Observer<List<LabelTemp>>() {
            @Override
            public void onChanged(@Nullable List<LabelTemp> labels) {
                listTempLabels = new ArrayList<>(labels);

            }
        });

        iv_back = v.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        et_label = v.findViewById(R.id.et_label);
        ll_add_label = v.findViewById(R.id.ll_add_label);
        ll_add_label.setVisibility(View.GONE);
        ll_add_label.setOnClickListener(this);
        tv_label = v.findViewById(R.id.tv_label);

        rv_label = v.findViewById(R.id.rv_label);
        rv_label.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LabelAdapter();
        rv_label.setAdapter(adapter);

        adapter.setOnCheckChangeListener(new LabelAdapter.onCheckboxEventListener() {
            @Override
            public void checkboxEvent(Label label) {
                LabelNote labelNote = new LabelNote(label.getId(), note_id);
                if (isPresent(label)){
                    if (label.isChecked()){
                        labelNoteViewModel.update(labelNote);
                    }
                }

               /* LabelTemp labelTemp = new LabelTemp(label.getId(), label.getTitle(), label.getNote_id(), label.isChecked());
                if (isPresent(labelTemp)){
                    System.out.println("label present: " + isPresent(labelTemp));
                    if (label.isChecked()){
                        System.out.println("checked: " + isPresent(labelTemp));
                        labelTempViewModel.update(new LabelTemp(label.getId(), label.getTitle(), label.getNote_id(), label.isChecked()));
                    }else {
                        System.out.println("unchecked: " + isPresent(labelTemp));
                        labelTempViewModel.delete(new LabelTemp(label.getId(), label.getTitle(), label.getNote_id(), label.isChecked()));
                    }
                }else {
                    System.out.println("label absent: " + isPresent(labelTemp));
                    labelTempViewModel.insert(new LabelTemp(label.getId(), label.getTitle(), label.getNote_id(), label.isChecked()));
                }*/
            }
        });

        et_label.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) {
                    timer.cancel();
                }
                if (s.length() == 0) {
                    ll_add_label.setVisibility(View.GONE);
                } else if (s.length() > 0) {
                    ll_add_label.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(final Editable s) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // do your actual work here
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                String string = s.toString().trim();
                                filter(string);

                                if (string.length() != 0){
                                    if (listLabelTitle.contains(string)){
                                        ll_add_label.setVisibility(View.GONE);

                                    }else {
                                        ll_add_label.setVisibility(View.VISIBLE);
                                        tv_label.setText("Add label '" + string +"'");
                                    }
                                }

                            }
                        });

                    }
                }, 600);
            }
        });


        return v;
    }

    void filter(String text){
        ArrayList<Label> temp = new ArrayList();
        for(Label d: listLabel){
            if(d.getTitle().contains(text)){
                temp.add(d);
            }
        }

        //update recyclerView
        adapter.submitList(temp);
    }

    private boolean isPresent(Label label){
        for (LabelNote l: listLabelNote) {
            if (l.getLabel_id() == label.getId() && l.getNote_id() == note_id)
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.ll_add_label:
                String label = tv_label.getText().toString().trim();

                labelViewModel.insert(new Label(label.substring(11, label.length()-1), note_id));
                break;
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
        }
    }

}
