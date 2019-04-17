package com.allen.pad.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.allen.pad.Database.LabelTemp.LabelTempRepository;
import com.allen.pad.Model.LabelTemp;
import java.util.List;

public class LabelTempViewModel extends AndroidViewModel {
    private LabelTempRepository repository;
    private LiveData<List<LabelTemp>> allLabels;

    public LabelTempViewModel(@NonNull Application application) {
        super(application);
        repository = new LabelTempRepository(application);
        allLabels = repository.getAllLabels();
    }

    public void insert(LabelTemp LabelTemp){
        repository.insert(LabelTemp);
    }

    public void update(LabelTemp LabelTemp){
        repository.update(LabelTemp);
    }

    public void delete(LabelTemp LabelTemp){
        repository.delete(LabelTemp);
    }

    public void deleteAllLabels(){
        repository.deleteAllLabels();
    }

    public LiveData<List<LabelTemp>> getAllLabels(){
        return allLabels;
    }

    public LiveData<List<LabelTemp>> getNoteLabels(int id){
        return repository.getNoteLabels(id);
    }


}
