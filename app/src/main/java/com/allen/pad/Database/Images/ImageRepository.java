package com.allen.pad.Database.Images;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.allen.pad.Database.NoteDatabase;
import com.allen.pad.Model.Image;
import java.util.List;

public class ImageRepository {
    private ImageDao imageDao;
    private LiveData<List<Image>> allImages;

    public ImageRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        imageDao = database.imageDao();
        allImages = imageDao.getAllImages();
    }

    public void insert(Image image){
        new InsertImageAsyncTask(imageDao).execute(image);
    }

    public void update(Image image){
        new UpdateImageAsyncTask(imageDao).execute(image);
    }

    public void delete(Image image){
        new DeleteImageAsyncTask(imageDao).execute(image);
    }

    public void deleteAllImages(){
        new DeleteAllImageAsyncTask(imageDao).execute();
    }

    public LiveData<List<Image>> getAllImages() {
        return allImages;
    }

    private static class InsertImageAsyncTask extends AsyncTask<Image, Void, Void> {
        private ImageDao imageDao;

        private InsertImageAsyncTask(ImageDao imageDao){
            this.imageDao = imageDao;
        }

        @Override
        protected Void doInBackground(Image... images) {
            imageDao.insert(images[0]);
            return null;
        }
    }

    private static class UpdateImageAsyncTask extends AsyncTask<Image, Void, Void> {
        private ImageDao imageDao;

        private UpdateImageAsyncTask(ImageDao imageDao){
            this.imageDao = imageDao;
        }

        @Override
        protected Void doInBackground(Image... images) {
            imageDao.update(images[0]);
            return null;
        }
    }

    private static class DeleteImageAsyncTask extends AsyncTask<Image, Void, Void> {
        private ImageDao imageDao;

        private DeleteImageAsyncTask(ImageDao imageDao){
            this.imageDao = imageDao;
        }

        @Override
        protected Void doInBackground(Image... images) {
            imageDao.delete(images[0]);
            return null;
        }
    }

    private static class DeleteAllImageAsyncTask extends AsyncTask<Image, Void, Void> {
        private ImageDao imageDao;

        private DeleteAllImageAsyncTask(ImageDao imageDao){
            this.imageDao = imageDao;
        }

        @Override
        protected Void doInBackground(Image... images) {
            imageDao.deleteAllImages();
            return null;
        }
    }
}
