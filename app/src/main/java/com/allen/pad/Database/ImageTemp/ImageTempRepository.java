package com.allen.pad.Database.ImageTemp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.allen.pad.Database.NoteDatabase;
import com.allen.pad.Model.ImageTemp;

import java.util.List;

public class ImageTempRepository {
    private ImageTempDao imageTempDao;
    private LiveData<List<ImageTemp>> allImages;

    public ImageTempRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        imageTempDao = database.imageTempDao();
        allImages = imageTempDao.getAllImages();
    }

    public void insert(ImageTemp imageTemp){
        new InsertImageAsyncTask(imageTempDao).execute(imageTemp);
    }

    public void update(ImageTemp imageTemp){
        new UpdateImageAsyncTask(imageTempDao).execute(imageTemp);
    }

    public void delete(ImageTemp imageTemp){
        new DeleteImageAsyncTask(imageTempDao).execute(imageTemp);
    }

    public void deleteAllImages(){
        new DeleteAllImageAsyncTask(imageTempDao).execute();
    }

    public LiveData<List<ImageTemp>> getAllImages() {
        return allImages;
    }

    public LiveData<List<ImageTemp>> getNoteImages(int id) {
        return imageTempDao.getNoteImages(id);
    }

    private static class InsertImageAsyncTask extends AsyncTask<ImageTemp, Void, Void> {
        private ImageTempDao imageTempDao;

        private InsertImageAsyncTask(ImageTempDao imageTempDao){
            this.imageTempDao = imageTempDao;
        }

        @Override
        protected Void doInBackground(ImageTemp... images) {
            imageTempDao.insert(images[0]);
            return null;
        }
    }

    private static class UpdateImageAsyncTask extends AsyncTask<ImageTemp, Void, Void> {
        private ImageTempDao imageTempDao;

        private UpdateImageAsyncTask(ImageTempDao imageTempDao){
            this.imageTempDao = imageTempDao;
        }

        @Override
        protected Void doInBackground(ImageTemp... images) {
            imageTempDao.update(images[0]);
            return null;
        }
    }

    private static class DeleteImageAsyncTask extends AsyncTask<ImageTemp, Void, Void> {
        private ImageTempDao imageTempDao;

        private DeleteImageAsyncTask(ImageTempDao imageTempDao){
            this.imageTempDao = imageTempDao;
        }

        @Override
        protected Void doInBackground(ImageTemp... images) {
            imageTempDao.delete(images[0]);
            return null;
        }
    }

    private static class DeleteAllImageAsyncTask extends AsyncTask<ImageTemp, Void, Void> {
        private ImageTempDao imageTempDao;

        private DeleteAllImageAsyncTask(ImageTempDao imageTempDao){
            this.imageTempDao = imageTempDao;
        }

        @Override
        protected Void doInBackground(ImageTemp... images) {
            imageTempDao.deleteAllImages();
            return null;
        }
    }
}
