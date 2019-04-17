package com.allen.pad.Database.LabelNote;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.allen.pad.Database.Label.LabelDao;
import com.allen.pad.Database.LabelNote.LabelNoteDao;
import com.allen.pad.Database.NoteDatabase;
import com.allen.pad.Model.Label;
import com.allen.pad.Model.LabelNote;

import java.util.List;

public class LabelNoteRepository {
    private LabelNoteDao labelNoteDao;
    private LabelDao labelDao;
    private LiveData<List<LabelNote>> allLabels;
    private LiveData<List<Label>> allLabel;

    public LabelNoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        labelNoteDao = database.labelNoteDao();
        allLabels = labelNoteDao.getAllLabel();
        allLabel =  labelDao.getAllLabel();
    }

    public void insert(LabelNote labelNote){
        new InsertLabelAsyncTask(labelNoteDao).execute(labelNote);
    }

    public void update(LabelNote labelNote){
        new UpdateLabelAsyncTask(labelNoteDao).execute(labelNote);
    }

    public void delete(LabelNote labelNote){
        new DeleteLabelAsyncTask(labelNoteDao).execute(labelNote);
    }

    public void deleteAllLabels(){
        new DeleteAllLabelAsyncTask(labelNoteDao).execute();
    }

    public LiveData<List<LabelNote>> getAllLabels() {
        return allLabels;
    }

    public LiveData<List<LabelNote>> getNoteLabels(int id) {
        return labelNoteDao.getNoteLabels(id);
    }

    public LiveData<List<Label>> getLabels(int id) {
        return labelDao.getNoteLabels(id);
    }

    private static class InsertLabelAsyncTask extends AsyncTask<LabelNote, Void, Void> {
        private LabelNoteDao labelNoteDao;

        private InsertLabelAsyncTask(LabelNoteDao labelNoteDao){
            this.labelNoteDao = labelNoteDao;
        }

        @Override
        protected Void doInBackground(LabelNote... labels) {
            labelNoteDao.insert(labels[0]);
            return null;
        }
    }

    private static class UpdateLabelAsyncTask extends AsyncTask<LabelNote, Void, Void> {
        private LabelNoteDao labelNoteDao;

        private UpdateLabelAsyncTask(LabelNoteDao labelNoteDao){
            this.labelNoteDao = labelNoteDao;
        }

        @Override
        protected Void doInBackground(LabelNote... labels) {
            labelNoteDao.update(labels[0]);
            return null;
        }
    }

    private static class DeleteLabelAsyncTask extends AsyncTask<LabelNote, Void, Void> {
        private LabelNoteDao labelNoteDao;

        private DeleteLabelAsyncTask(LabelNoteDao labelNoteDao){
            this.labelNoteDao = labelNoteDao;
        }

        @Override
        protected Void doInBackground(LabelNote... labels) {
            labelNoteDao.delete(labels[0]);
            return null;
        }
    }

    private static class DeleteAllLabelAsyncTask extends AsyncTask<LabelNote, Void, Void> {
        private LabelNoteDao labelNoteDao;

        private DeleteAllLabelAsyncTask(LabelNoteDao labelNoteDao){
            this.labelNoteDao = labelNoteDao;
        }

        @Override
        protected Void doInBackground(LabelNote... labels) {
            labelNoteDao.deleteAllLabel();
            return null;
        }
    }
}
