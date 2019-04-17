package com.allen.pad.Database.LabelTemp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.allen.pad.Database.LabelTemp.LabelTempDao;
import com.allen.pad.Database.NoteDatabase;
import com.allen.pad.Model.LabelTemp;

import java.util.List;

public class LabelTempRepository {
    private LabelTempDao labelTempDao;
    private LiveData<List<LabelTemp>> allLabels;

    public LabelTempRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        labelTempDao = database.labelTempDao();
        allLabels = labelTempDao.getAllLabel();
    }

    public void insert(LabelTemp labelTemp){
        new InsertLabelAsyncTask(labelTempDao).execute(labelTemp);
    }

    public void update(LabelTemp labelTemp){
        new UpdateLabelAsyncTask(labelTempDao).execute(labelTemp);
    }

    public void delete(LabelTemp labelTemp){
        new DeleteLabelAsyncTask(labelTempDao).execute(labelTemp);
    }

    public void deleteAllLabels(){
        new DeleteAllLabelAsyncTask(labelTempDao).execute();
    }

    public LiveData<List<LabelTemp>> getAllLabels() {
        return allLabels;
    }

    public LiveData<List<LabelTemp>> getNoteLabels(int id) {
        return labelTempDao.getNoteLabels(id);
    }

    private static class InsertLabelAsyncTask extends AsyncTask<LabelTemp, Void, Void> {
        private LabelTempDao labelTempDao;

        private InsertLabelAsyncTask(LabelTempDao labelTempDao){
            this.labelTempDao = labelTempDao;
        }

        @Override
        protected Void doInBackground(LabelTemp... labels) {
            labelTempDao.insert(labels[0]);
            return null;
        }
    }

    private static class UpdateLabelAsyncTask extends AsyncTask<LabelTemp, Void, Void> {
        private LabelTempDao labelTempDao;

        private UpdateLabelAsyncTask(LabelTempDao labelTempDao){
            this.labelTempDao = labelTempDao;
        }

        @Override
        protected Void doInBackground(LabelTemp... labels) {
            labelTempDao.update(labels[0]);
            return null;
        }
    }

    private static class DeleteLabelAsyncTask extends AsyncTask<LabelTemp, Void, Void> {
        private LabelTempDao labelTempDao;

        private DeleteLabelAsyncTask(LabelTempDao labelTempDao){
            this.labelTempDao = labelTempDao;
        }

        @Override
        protected Void doInBackground(LabelTemp... labels) {
            labelTempDao.delete(labels[0]);
            return null;
        }
    }

    private static class DeleteAllLabelAsyncTask extends AsyncTask<LabelTemp, Void, Void> {
        private LabelTempDao labelTempDao;

        private DeleteAllLabelAsyncTask(LabelTempDao labelTempDao){
            this.labelTempDao = labelTempDao;
        }

        @Override
        protected Void doInBackground(LabelTemp... labels) {
            labelTempDao.deleteAllLabel();
            return null;
        }
    }
}
