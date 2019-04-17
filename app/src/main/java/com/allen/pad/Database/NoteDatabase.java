package com.allen.pad.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.allen.pad.Database.ImageTemp.ImageTempDao;
import com.allen.pad.Database.Images.ImageDao;
import com.allen.pad.Database.Label.LabelDao;
import com.allen.pad.Database.LabelNote.LabelNoteDao;
import com.allen.pad.Database.LabelTemp.LabelTempDao;
import com.allen.pad.Database.Note.NoteDao;
import com.allen.pad.Database.Tickbox.TickboxDao;
import com.allen.pad.Model.Image;
import com.allen.pad.Model.ImageTemp;
import com.allen.pad.Model.Label;
import com.allen.pad.Model.LabelNote;
import com.allen.pad.Model.LabelTemp;
import com.allen.pad.Model.Note;
import com.allen.pad.Model.Tickbox;
import com.allen.pad.Utility.AppUtils;

@Database(entities = {Note.class, Label.class, LabelTemp.class, LabelNote.class, Tickbox.class, Image.class, ImageTemp.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public abstract ImageDao imageDao();
    public abstract ImageTempDao imageTempDao();

    public abstract LabelDao labelDao();
    public abstract LabelTempDao labelTempDao();
    public abstract LabelNoteDao labelNoteDao();

    public abstract TickboxDao tickboxDao();

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
            noteDao.insert(new Note("Title 1", "Description 1", android.R.color.white, AppUtils.getCurrentDateTime(), AppUtils.getCurrentDateTime(), 1));
            noteDao.insert(new Note("Title 2", "Description 2", android.R.color.white, AppUtils.getCurrentDateTime(), AppUtils.getCurrentDateTime(), 2));
            noteDao.insert(new Note("Title 3", "Description 3", android.R.color.white, AppUtils.getCurrentDateTime(), AppUtils.getCurrentDateTime(), 3));

            labelDao.insert(new Label("Title 1", -1));
            labelDao.insert(new Label("Title 2", -1));
            labelDao.insert(new Label("Title 3", -1));

            tickboxDao.insert(new Tickbox(""));

            return null;
        }
    }

}
