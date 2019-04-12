package com.allen.pad.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.allen.pad.Database.Images.ImageDao;
import com.allen.pad.Database.Label.LabelDao;
import com.allen.pad.Database.Tickbox.TickboxDao;
import com.allen.pad.Model.Image;
import com.allen.pad.Model.Label;
import com.allen.pad.Model.Note;
import com.allen.pad.Model.Tickbox;
import com.allen.pad.Utility.AppUtils;
import java.util.ArrayList;

@Database(entities = {Note.class, Label.class, Tickbox.class, Image.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();
    public abstract LabelDao labelDao();
    public abstract TickboxDao tickboxDao();
    public abstract ImageDao imageDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                        NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;
        private LabelDao labelDao;
        private TickboxDao tickboxDao;

        private PopulateDbAsyncTask(NoteDatabase db){
            noteDao = db.noteDao();
            labelDao = db.labelDao();
            tickboxDao = db.tickboxDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Description 1", new ArrayList<String>(),new ArrayList<String>(), AppUtils.getCurrentDateTime(), AppUtils.getCurrentDateTime(), 1));
            noteDao.insert(new Note("Title 2", "Description 2", new ArrayList<String>(), new ArrayList<String>(),AppUtils.getCurrentDateTime(), AppUtils.getCurrentDateTime(), 2));
            noteDao.insert(new Note("Title 3", "Description 3", new ArrayList<String>(), new ArrayList<String>(),AppUtils.getCurrentDateTime(), AppUtils.getCurrentDateTime(), 3));

            labelDao.insert(new Label("Title 1"));
            labelDao.insert(new Label("Title 2"));
            labelDao.insert(new Label("Title 3"));

            tickboxDao.insert(new Tickbox(""));



            return null;
        }
    }

}
