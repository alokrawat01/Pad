package com.allen.pad.Database.Label;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.allen.pad.Database.NoteDatabase;
import com.allen.pad.Model.Label;
import java.util.List;

public class LabelRepository {
    private LabelDao labelDao;
    private LiveData<List<Label>> allLabels;

    public LabelRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        labelDao = database.labelDao();
        allLabels = labelDao.getAllLabel();
    }

    public void insert(Label label){
        new InsertLabelAsyncTask(labelDao).execute(label);
    }

    public void update(Label label){
        new UpdateLabelAsyncTask(labelDao).execute(label);
    }

    public void delete(Label label){
        new DeleteLabelAsyncTask(labelDao).execute(label);
    }

    public void deleteAllLabels(){
        new DeleteAllLabelAsyncTask(labelDao).execute();
    }

    public LiveData<List<Label>> getAllLabels() {
        return allLabels;
    }

    public LiveData<List<Label>> getNoteLabels(int id) {
        return labelDao.getNoteLabels(id);
    }

    private static class InsertLabelAsyncTask extends AsyncTask<Label, Void, Void> {
        private LabelDao labelDao;

        private InsertLabelAsyncTask(LabelDao labelDao){
            this.labelDao = labelDao;
        }

        @Override
        protected Void doInBackground(Label... labels) {
            labelDao.insert(labels[0]);
            return null;
        }
    }

    private static class UpdateLabelAsyncTask extends AsyncTask<Label, Void, Void> {
        private LabelDao labelDao;

        private UpdateLabelAsyncTask(LabelDao labelDao){
            this.labelDao = labelDao;
        }

        @Override
        protected Void doInBackground(Label... labels) {
            labelDao.update(labels[0]);
            return null;
        }
    }

    private static class DeleteLabelAsyncTask extends AsyncTask<Label, Void, Void> {
        private LabelDao labelDao;

        private DeleteLabelAsyncTask(LabelDao labelDao){
            this.labelDao = labelDao;
        }

        @Override
        protected Void doInBackground(Label... labels) {
            labelDao.delete(labels[0]);
            return null;
        }
    }

    private static class DeleteAllLabelAsyncTask extends AsyncTask<Label, Void, Void> {
        private LabelDao labelDao;

        private DeleteAllLabelAsyncTask(LabelDao labelDao){
            this.labelDao = labelDao;
        }

        @Override
        protected Void doInBackground(Label... labels) {
            labelDao.deleteAllLabel();
            return null;
        }
    }
}
