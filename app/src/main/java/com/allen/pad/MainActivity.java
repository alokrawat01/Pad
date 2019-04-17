package com.allen.pad;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.allen.pad.Adapter.NoteAdapter;
import com.allen.pad.AddNote.AddEditNoteActivity;
import com.allen.pad.Model.Label;
import com.allen.pad.Model.Note;
import com.allen.pad.ViewModel.NoteViewModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    RecyclerView rv_home;
    FloatingActionButton fab;
    private LinearLayout layoutFabNote;
    private LinearLayout layoutFabEdit;
    private LinearLayout layoutFabPhoto;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private NoteViewModel noteViewModel;
    List<Note> notes;
    private boolean fabExpanded = false;
    int temp_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }


            }
        });

        layoutFabNote = findViewById(R.id.layoutFabNote);
        layoutFabNote.setOnClickListener(this);
        layoutFabEdit = findViewById(R.id.layoutFabEdit);
        layoutFabPhoto = findViewById(R.id.layoutFabPhoto);

        closeSubMenusFab();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rv_home = findViewById(R.id.rv_home);
        rv_home.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        notes = new ArrayList<>();
        final NoteAdapter adapter = new NoteAdapter();
        rv_home.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                temp_id = (notes.size()+1);
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv_home);

        adapter.setOnItemClickListener(new NoteAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_COLOR, note.getColor());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title =  data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description =  data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            Date created_time = new Date(data.getLongExtra(AddEditNoteActivity.EXTRA_CREATED_TIME, 1));
            Date modified_time = new Date(data.getLongExtra(AddEditNoteActivity.EXTRA_MODIFIED_TIME, 1));
            int color = data.getIntExtra(AddEditNoteActivity.EXTRA_COLOR, android.R.color.white);
            int priority =  data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            noteViewModel.insert(new Note(title, description, color, created_time, modified_time, priority));

            Toast.makeText(MainActivity.this, "Note saved", Toast.LENGTH_SHORT).show();
        }else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if (id == -1){
                Toast.makeText(MainActivity.this, "Note can't be updated", Toast.LENGTH_SHORT).show();
            }

            String title =  data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description =  data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            Date created_time = new Date(data.getLongExtra(AddEditNoteActivity.EXTRA_CREATED_TIME, 1));
            Date modified_time = new Date(data.getLongExtra(AddEditNoteActivity.EXTRA_MODIFIED_TIME, 1));
            int color = data.getIntExtra(AddEditNoteActivity.EXTRA_COLOR, android.R.color.white);
            int priority =  data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title, description, color, created_time, modified_time, priority);
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(MainActivity.this, "Note updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }


    private void closeSubMenusFab(){
        layoutFabNote.setVisibility(View.INVISIBLE);
        layoutFabEdit.setVisibility(View.INVISIBLE);
        layoutFabPhoto.setVisibility(View.INVISIBLE);
        fab.setImageResource(R.drawable.ic_add);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabNote.setVisibility(View.VISIBLE);
        layoutFabEdit.setVisibility(View.VISIBLE);
        layoutFabPhoto.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fab.setImageResource(R.drawable.ic_close);
        fabExpanded = true;
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.layoutFabNote:
                closeSubMenusFab();
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra("TEMP_ID", temp_id);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
                break;
        }
    }
}
