package com.allen.pad.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.allen.pad.Database.ImageTemp.ImageTempRepository;
import com.allen.pad.Model.ImageTemp;
import java.util.List;

public class ImageTempViewModel extends AndroidViewModel {
    private ImageTempRepository repository;
    private LiveData<List<ImageTemp>> allImageTemps;

    public ImageTempViewModel(@NonNull Application application) {
        super(application);
        repository = new ImageTempRepository(application);
        allImageTemps = repository.getAllImages();
    }

    public void insert(ImageTemp image){
        repository.insert(image);
    }

    public void update(ImageTemp image){
        repository.update(image);
    }

    public void delete(ImageTemp image){
        repository.delete(image);
    }

    public void deleteAllImageTemps(){
        repository.deleteAllImages();
    }

    public LiveData<List<ImageTemp>> getAllImageTemps(){
        return allImageTemps;
    }

    public LiveData<List<ImageTemp>> getNoteImageTemps(int id){
        return repository.getNoteImages(id);
    }

}
