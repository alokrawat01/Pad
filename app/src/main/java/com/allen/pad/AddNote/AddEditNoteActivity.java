package com.allen.pad.AddNote;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
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
import com.allen.pad.Adapter.ImageAdapter;
import com.allen.pad.Adapter.LabelAdapter;
import com.allen.pad.Adapter.LabelAddEditAdapter;
import com.allen.pad.Adapter.TickboxAdapter;
import com.allen.pad.Model.Color;
import com.allen.pad.Model.Image;
import com.allen.pad.Model.ImageTemp;
import com.allen.pad.Model.Label;
import com.allen.pad.Model.LabelTemp;
import com.allen.pad.Model.Tickbox;
import com.allen.pad.R;
import com.allen.pad.Utility.AppUtils;
import com.allen.pad.Utility.draw.activity.DrawingActivity;
import com.allen.pad.ViewModel.ImageTempViewModel;
import com.allen.pad.ViewModel.ImageViewModel;
import com.allen.pad.ViewModel.LabelTempViewModel;
import com.allen.pad.ViewModel.LabelViewModel;
import com.allen.pad.ViewModel.TickboxViewModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddEditNoteActivity extends AppCompatActivity implements View.OnClickListener, LabelAdapter.onCheckboxEventListener {
    public static final String EXTRA_ID = "com.allen.pad.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.allen.pad.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.allen.pad.EXTRA_DESCRIPTION";
    public static final String EXTRA_COLOR = "com.allen.pad.EXTRA_COLOR";
    public static final String EXTRA_CREATED_TIME = "com.allen.pad.EXTRA_CREATED_TIME";
    public static final String EXTRA_MODIFIED_TIME = "com.allen.pad.EXTRA_MODIFIED_TIME";
    public static final String EXTRA_PRIORITY = "com.allen.pad.EXTRA_PRIORITY";

    CoordinatorLayout cl_add_note;
    EditText et_title;
    EditText et_description;
    RecyclerView rv_label;
    RecyclerView rv_tickbox;
    RecyclerView rv_images;
    TextView tv_add_cb;

    NumberPicker np_priority;
    ImageView iv_add;
    ImageView iv_menu;
    LinearLayout ll_add;
    TextView tv_drawing;
    LinearLayout ll_menu;
    RecyclerView rv_color;
    TextView tv_label;

    ImageTempViewModel imageTempViewModel;
    ArrayList<ImageTemp> listTempImages;
    ImageViewModel imageViewModel;
    ArrayList<Image> listImages;

    LabelTempViewModel labelTempViewModel;
    ArrayList<LabelTemp> listTempLabels;
    LabelViewModel labelViewModel;
    ArrayList<Label> listLabels;

    TickboxViewModel tickboxViewModel;
    ImageAdapter imageAdapter;
    LabelAddEditAdapter labelAddAdapter;
    TickboxAdapter tickboxAdapter;
    ArrayList<Tickbox> listTickboxes;
    ColorAdapter adapter;
    List<Color> colors;

    boolean isAddVisible = false;
    boolean isMenuVisible = false;
    int bg_color;
    Date createdTime;
    Date modifiedTime;
    int note_id;

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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        initializeViews();

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            note_id = intent.getIntExtra(EXTRA_ID, -1);
            et_title.setText(intent.getStringExtra(EXTRA_TITLE));
            et_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            bg_color = intent.getIntExtra(EXTRA_COLOR, android.R.color.white);
            createdTime = new Date(intent.getLongExtra(AddEditNoteActivity.EXTRA_CREATED_TIME, 1));
            modifiedTime = new Date(intent.getLongExtra(AddEditNoteActivity.EXTRA_MODIFIED_TIME, 1));
            np_priority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));

            imageTempViewModel = ViewModelProviders.of(this).get(ImageTempViewModel.class);
            imageTempViewModel.getNoteImageTemps(note_id).observe(this, new Observer<List<ImageTemp>>() {
                @Override
                public void onChanged(@Nullable List<ImageTemp> images) {
                    if (images != null) {
                        System.out.println("AddEdit: noteTempImages: " + images.size());
                        listTempImages = new ArrayList<>(images);
                        imageAdapter.submitList(listTempImages);
                    }
                }
            });

            imageViewModel = ViewModelProviders.of(this).get(ImageViewModel.class);
            imageViewModel.getNoteImages(note_id).observe(this, new Observer<List<Image>>() {
                @Override
                public void onChanged(@Nullable List<Image> images) {
                    // New note image list is empty i.e. size zero
                    if (images != null) {
                        System.out.println("AddEdit: noteImages: " + images.size());
                        listImages = new ArrayList<>(images);
                        for (Image i : listImages) {
                            imageTempViewModel.insert(new ImageTemp(i.getTitle(), i.getNote_id()));
                        }
                    }
                }
            });

            labelTempViewModel = ViewModelProviders.of(this).get(LabelTempViewModel.class);
            labelTempViewModel.getAllLabels().observe(this, new Observer<List<LabelTemp>>() {
                @Override
                public void onChanged(@Nullable List<LabelTemp> labels) {
                    // New note label list is empty i.e. size zero
                    if (labels != null) {
                        System.out.println("Edit: noteTempLabels: " + labels.size());
                        listTempLabels = new ArrayList<>(labels);
                        labelAddAdapter.submitList(listTempLabels);

                    }
                }
            });

            labelViewModel = ViewModelProviders.of(this).get(LabelViewModel.class);
            labelViewModel.getNoteLabels(note_id).observe(this, new Observer<List<Label>>() {
                @Override
                public void onChanged(@Nullable List<Label> labels) {
                    // New note label list is empty i.e. size zero
                    if (labels != null) {
                        System.out.println("Edit: noteLabels: " + labels.size());
                        listLabels = new ArrayList<>(labels);
                        for (Label l: listLabels) {
                            labelTempViewModel.insert(new LabelTemp(l.getId(), l.getTitle(), l.getNote_id(), l.isChecked()));
                        }
                    }
                }
            });

        }else {
            setTitle("Add Note");
            note_id = intent.getIntExtra("TEMP_ID", -1);

            imageViewModel = ViewModelProviders.of(this).get(ImageViewModel.class);
            imageViewModel.getNoteImages(note_id).observe(this, new Observer<List<Image>>() {
                @Override
                public void onChanged(@Nullable List<Image> images) {
                    // New note image list is empty i.e. size zero
                    if (images != null) {
                        System.out.println("AddEdit: noteImages: " + images.size());
                        listImages = new ArrayList<>(images);
                    }
                }
            });

            imageTempViewModel = ViewModelProviders.of(this).get(ImageTempViewModel.class);
            imageTempViewModel.getNoteImageTemps(note_id).observe(this, new Observer<List<ImageTemp>>() {
                @Override
                public void onChanged(@Nullable List<ImageTemp> images) {
                    if (images != null) {
                        System.out.println("AddEdit: noteTempImages: " + images.size());
                        listTempImages = new ArrayList<>(images);
                        imageAdapter.submitList(listTempImages);
                    }
                }
            });


            labelViewModel = ViewModelProviders.of(this).get(LabelViewModel.class);
            labelViewModel.getNoteLabels(note_id).observe(this, new Observer<List<Label>>() {
                @Override
                public void onChanged(@Nullable List<Label> labels) {
                    // New note label list is empty i.e. size zero
                    System.out.println("AddEdit: noteLabels: " + labels.size());
                    listLabels = new ArrayList<>(labels);
                }
            });


            labelTempViewModel = ViewModelProviders.of(this).get(LabelTempViewModel.class);
            labelTempViewModel.getAllLabels().observe(this, new Observer<List<LabelTemp>>() {
                @Override
                public void onChanged(@Nullable List<LabelTemp> labels) {
                    // New note label list is empty i.e. size zero
                    System.out.println("AddEdit: noteTempLabels: " + labels.size());
                    listTempLabels = new ArrayList<>(labels);
                    for (LabelTemp l: labels) {
                        if (l.isChecked()){
                            listTempLabels.add(l);
                        }
                        labelAddAdapter.submitList(listTempLabels);
                    }
                }
            });
        }



        tickboxViewModel = ViewModelProviders.of(this).get(TickboxViewModel.class);
        tickboxViewModel.getAllTickboxs().observe(this, new Observer<List<Tickbox>>() {
            @Override
            public void onChanged(@Nullable List<Tickbox> tickboxes) {
                listTickboxes = new ArrayList<>(tickboxes);
                tickboxAdapter.submitList(listTickboxes);

            }
        });

        checkStoragePermission();

    }

    private void initializeViews(){
        cl_add_note = findViewById(R.id.cl_add_note);

        et_title = findViewById(R.id.et_title);

        et_description = findViewById(R.id.et_description);

        np_priority = findViewById(R.id.np_priority);
        np_priority.setMinValue(1);
        np_priority.setMaxValue(10);

        rv_images = findViewById(R.id.rv_images);
        rv_images.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        imageAdapter = new ImageAdapter(AddEditNoteActivity.this);
        rv_images.setAdapter(imageAdapter);

        imageAdapter.setOnItemRemoveListener(new ImageAdapter.onRemoveClickListener() {
            @Override
            public void onRemoveClick(ImageTemp image) {
                imageTempViewModel.delete(image);
            }
        });

        imageAdapter.setOnItemClickListener(new ImageAdapter.onItemClickListener() {
            @Override
            public void onItemClick(ImageTemp image) {
                ImageFragment imageFragment = new ImageFragment();

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("IMAGES", listImages);
                imageFragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.cl_add_note, imageFragment)
                        .addToBackStack("show_images")
                        .commit();
            }
        });

        rv_label = findViewById(R.id.rv_label);
        rv_label.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        labelAddAdapter = new LabelAddEditAdapter();
        rv_label.setAdapter(labelAddAdapter);

        labelAddAdapter.setOnItemClickListener(new LabelAddEditAdapter.onItemClickListener() {
            @Override
            public void onItemClick(LabelTemp labelTemp) {
                startLabelFragment();
            }
        });

        rv_tickbox = findViewById(R.id.rv_tickbox);
        rv_tickbox.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        tickboxAdapter = new TickboxAdapter();
        rv_tickbox.setAdapter(tickboxAdapter);

        tickboxAdapter.setOnItemRemoveListener(new TickboxAdapter.onRemoveClickListener() {
            @Override
            public void onItemClick(Tickbox tickbox) {
                tickboxViewModel.delete(tickbox);
            }
        });

        tickboxAdapter.setOnTextUpdateListener(new TickboxAdapter.onTextUpdateListener() {
            @Override
            public void onItemClick(final Tickbox tickbox) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        tickboxViewModel.update(tickbox);
                    }
                });
            }
        });

        tv_add_cb = findViewById(R.id.tv_add_cb);
        tv_add_cb.setOnClickListener(this);

        iv_add = findViewById(R.id.iv_add);
        iv_add.setOnClickListener(this);

        tv_drawing = findViewById(R.id.tv_drawing);
        tv_drawing.setOnClickListener(this);

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
                bg_color = color.getColor();
                cl_add_note.setBackgroundColor(bg_color);
                System.out.println("Color: " + color.getColor());
            }
        });

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
        data.putExtra(EXTRA_COLOR, bg_color);
        data.putExtra(EXTRA_CREATED_TIME, AppUtils.getCurrentDateTime());
        data.putExtra(EXTRA_MODIFIED_TIME, AppUtils.getCurrentDateTime());
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);

        for (ImageTemp i: listTempImages) {
            imageViewModel.insert(new Image(i.getTitle(), i.getNote_id()));
        }


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
            case R.id.tv_add_cb:
                    tickboxViewModel.insert(new Tickbox(""));
                break;

            case R.id.tv_drawing:
                Intent intent = new Intent(AddEditNoteActivity.this, DrawingActivity.class);
                startActivityForResult(intent, 101);
                break;
        }
    }


    private void startLabelFragment(){
        LabelFragment labelFragment = new LabelFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("NOTE_ID", note_id);
        labelFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.cl_add_note, labelFragment)
                .addToBackStack("add_note")
                .commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK){
            byte[] result = data.getByteArrayExtra("bitmap");
            Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
            saveImage(bitmap);
        }
    }

    public void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }else{
//                sheetDialog.show();
            }
        }else{
//            sheetDialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    sheetDialog.show();
                } else {
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getBaseContext());
                    alertDialogBuilder.setTitle("Storage Permission");
                    alertDialogBuilder
                            .setMessage("Allow Storage permission to add a profile image and have a seamless experience")
                            .setCancelable(false)
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    checkStoragePermission();
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
        }
    }

    private void saveImage(Bitmap bitmap) {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pad";
        File dir = new File(file_path);
        if(!dir.exists())
            dir.mkdirs();
        String file_name = "pad_" + System.currentTimeMillis()+ ".png";
        File file = new File(dir, file_name);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageTempViewModel.insert(new ImageTemp(file_path+"/"+file_name, note_id));
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
        }
        else super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
        imageTempViewModel.deleteAllImageTemps();
        labelTempViewModel.deleteAllLabels();
    }

    @Override
    public void checkboxEvent(Label labels) {

    }
}
