package com.allen.pad.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.allen.pad.Database.Images.ImageRepository;
import com.allen.pad.Model.Image;
import java.util.List;

public class ImageViewModel extends AndroidViewModel {
    private ImageRepository repository;
    private LiveData<List<Image>> allImages;

    public ImageViewModel(@NonNull Application application) {
        super(application);
        repository = new ImageRepository(application);
        allImages = repository.getAllImages();
    }

    public void insert(Image image){
        repository.insert(image);
    }

    public void update(Image image){
        repository.update(image);
    }

    public void delete(Image image){
        repository.delete(image);
    }

    public void deleteAllImages(){
        repository.deleteAllImages();
    }

    public LiveData<List<Image>> getAllImages(){
        return allImages;
    }

    public LiveData<List<Image>> getNoteImages(int id){
        return repository.getNoteImages(id);
    }

}
