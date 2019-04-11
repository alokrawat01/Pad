package com.allen.pad.AddNote;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.allen.pad.Adapter.ColorAdapter;
import com.allen.pad.Adapter.LabelAdapter;
import com.allen.pad.Adapter.LabelAddAdapter;
import com.allen.pad.Model.Color;
import com.allen.pad.Model.Label;
import com.allen.pad.R;
import com.allen.pad.Utility.AppUtils;
import com.allen.pad.ViewModel.LabelViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddEditNoteActivity extends AppCompatActivity implements View.OnClickListener, LabelAdapter.onCheckboxEventListener {
    public static final String EXTRA_ID = "com.allen.pad.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.allen.pad.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.allen.pad.EXTRA_DESCRIPTION";
    public static final String EXTRA_LABEL = "com.allen.pad.EXTRA_LABEL";
    public static final String EXTRA_CREATED_TIME = "com.allen.pad.EXTRA_CREATED_TIME";
    public static final String EXTRA_MODIFIED_TIME = "com.allen.pad.EXTRA_MODIFIED_TIME";
    public static final String EXTRA_PRIORITY = "com.allen.pad.EXTRA_PRIORITY";
    public static final int ADD_REQUEST = 1;

    CoordinatorLayout cl_add_note;
    EditText et_title;
    EditText et_description;
    RecyclerView rv_label;
    RecyclerView rl_tickbox;

    NumberPicker np_priority;
    ImageView iv_add;
    ImageView iv_menu;
    LinearLayout ll_add;
    LinearLayout ll_menu;
    RecyclerView rv_color;
    TextView tv_label;

    LabelViewModel viewModel;
    LabelAddAdapter labelAddAdapter;
    ArrayList<Label> listLabels;

    boolean isAddVisible = false;
    boolean isMenuVisible = false;
    List<Integer> listColor;
    List<Color> colors;
    ColorAdapter adapter;
    ArrayList<String> label = new ArrayList<>();
    Date createdTime;
    Date modifiedTime;

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        viewModel = ViewModelProviders.of(this).get(LabelViewModel.class);
        viewModel.getAllLabels().observe(this, new Observer<List<Label>>() {
            @Override
            public void onChanged(@Nullable List<Label> labels) {
                listLabels = new ArrayList<>();
                for (Label l: labels) {
                    if (l.isChecked()){
                        listLabels.add(l);
                    }
                    labelAddAdapter.submitList(listLabels);
                }
            }
        });

        cl_add_note = findViewById(R.id.cl_add_note);
        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);
        rv_label = findViewById(R.id.rv_label);
        rv_label.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        labelAddAdapter = new LabelAddAdapter();
        rv_label.setAdapter(labelAddAdapter);

        labelAddAdapter.setOnItemClickListener(new LabelAddAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Label note) {
                startLabelFragment();
            }
        });


        np_priority = findViewById(R.id.np_priority);
        iv_add = findViewById(R.id.iv_add);
        iv_add.setOnClickListener(this);

        iv_menu = findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(this);

        ll_add = findViewById(R.id.ll_add);
        ll_add.setVisibility(View.GONE);

        ll_menu = findViewById(R.id.ll_menu);
        ll_menu.setVisibility(View.GONE);

        rv_color = findViewById(R.id.rv_color);
        rv_color.setLayoutManager(new LinearLayoutManager(AddEditNoteActivity.this, LinearLayoutManager.HORIZONTAL, false));
        colors = new ArrayList<>();
        colors.add(new Color(ContextCompat.getColor(getBaseContext(), android.R.color.white), true));
        colors.add(new Color(ContextCompat.getColor(getBaseContext(), android.R.color.holo_red_dark), false));
        colors.add(new Color(ContextCompat.getColor(getBaseContext(), android.R.color.holo_blue_dark), false));
        colors.add(new Color(ContextCompat.getColor(getBaseContext(), android.R.color.holo_green_dark), false));
        colors.add(new Color(ContextCompat.getColor(getBaseContext(), android.R.color.holo_orange_dark), false));
        adapter = new ColorAdapter(colors, AddEditNoteActivity.this);
        rv_color.setAdapter(adapter);

        tv_label = findViewById(R.id.tv_label);
        tv_label.setOnClickListener(this);

        adapter.setOnItemClickListener(new ColorAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Color color) {
                cl_add_note.setBackgroundColor(color.getColor());
                System.out.println("Color: " + color.getColor());
            }
        });

        np_priority.setMinValue(1);
        np_priority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            et_title.setText(intent.getStringExtra(EXTRA_TITLE));
            et_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            label = intent.getStringArrayListExtra(EXTRA_LABEL);
            createdTime = new Date(intent.getLongExtra(AddEditNoteActivity.EXTRA_CREATED_TIME, 1));
            modifiedTime = new Date(intent.getLongExtra(AddEditNoteActivity.EXTRA_MODIFIED_TIME, 1));
            np_priority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        }else {
            setTitle("Add Note");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = et_title.getText().toString().trim();
        String description = et_description.getText().toString().trim();
        int priority = np_priority.getValue();

        if (title.isEmpty() || description.isEmpty()){
            Toast.makeText(AddEditNoteActivity.this, "Title and description can't be empty", Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putStringArrayListExtra(EXTRA_LABEL, label);
        data.putExtra(EXTRA_CREATED_TIME, AppUtils.getCurrentDateTime());
        data.putExtra(EXTRA_MODIFIED_TIME, AppUtils.getCurrentDateTime());
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.iv_add:
                ll_menu.setVisibility(View.GONE);
                isMenuVisible = false;

                if (isAddVisible){
                    ll_add.setVisibility(View.GONE);
                }else {
                    ll_add.setVisibility(View.VISIBLE);
                }
                isAddVisible = !isAddVisible;
                break;
            case R.id.iv_menu:
                ll_add.setVisibility(View.GONE);
                isAddVisible = false;

                if (isMenuVisible){
                    ll_menu.setVisibility(View.GONE);
                }else {
                    ll_menu.setVisibility(View.VISIBLE);
                }
                isMenuVisible = !isMenuVisible;
                break;
            case R.id.tv_label:
                ll_menu.setVisibility(View.GONE);
                isMenuVisible = false;

                startLabelFragment();

                break;

        }
    }

    private void startLabelFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.cl_add_note, new LabelFragment())
                .addToBackStack("add_note")
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
        }
        else super.onBackPressed();
    }

    @Override
    public void checkboxEvent(Label labels) {

    }
}
