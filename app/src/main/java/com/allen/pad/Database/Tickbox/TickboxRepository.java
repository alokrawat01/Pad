package com.allen.pad.Database.Tickbox;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.allen.pad.Database.NoteDatabase;
import com.allen.pad.Model.Tickbox;
import java.util.List;

public class TickboxRepository {
    private TickboxDao tickboxDao;
    private LiveData<List<Tickbox>> allTickboxs;

    public TickboxRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        tickboxDao = database.tickboxDao();
        allTickboxs = tickboxDao.getAllTickbox();
    }

    public void insert(Tickbox tickbox){
        new InsertTickboxAsyncTask(tickboxDao).execute(tickbox);
    }

    public void update(Tickbox tickbox){
        new UpdateTickboxAsyncTask(tickboxDao).execute(tickbox);
    }

    public void delete(Tickbox tickbox){
        new DeleteTickboxAsyncTask(tickboxDao).execute(tickbox);
    }

    public void deleteAllTickboxs(){
        new DeleteAllTickboxAsyncTask(tickboxDao).execute();
    }

    public LiveData<List<Tickbox>> getAllTickboxs() {
        return allTickboxs;
    }

    private static class InsertTickboxAsyncTask extends AsyncTask<Tickbox, Void, Void> {
        private TickboxDao tickboxDao;

        private InsertTickboxAsyncTask(TickboxDao tickboxDao){
            this.tickboxDao = tickboxDao;
        }

        @Override
        protected Void doInBackground(Tickbox... tickboxs) {
            tickboxDao.insert(tickboxs[0]);
            return null;
        }
    }

    private static class UpdateTickboxAsyncTask extends AsyncTask<Tickbox, Void, Void> {
        private TickboxDao tickboxDao;

        private UpdateTickboxAsyncTask(TickboxDao tickboxDao){
            this.tickboxDao = tickboxDao;
        }

        @Override
        protected Void doInBackground(Tickbox... tickboxs) {
            tickboxDao.update(tickboxs[0]);
            return null;
        }
    }

    private static class DeleteTickboxAsyncTask extends AsyncTask<Tickbox, Void, Void> {
        private TickboxDao tickboxDao;

        private DeleteTickboxAsyncTask(TickboxDao tickboxDao){
            this.tickboxDao = tickboxDao;
        }

        @Override
        protected Void doInBackground(Tickbox... tickboxs) {
            tickboxDao.delete(tickboxs[0]);
            return null;
        }
    }

    private static class DeleteAllTickboxAsyncTask extends AsyncTask<Tickbox, Void, Void> {
        private TickboxDao tickboxDao;

        private DeleteAllTickboxAsyncTask(TickboxDao tickboxDao){
            this.tickboxDao = tickboxDao;
        }

        @Override
        protected Void doInBackground(Tickbox... tickboxs) {
            tickboxDao.deleteAllTickbox();
            return null;
        }
    }
}
