package com.allen.pad.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.allen.pad.Database.Label.LabelRepository;
import com.allen.pad.Model.Label;
import java.util.List;

public class LabelViewModel extends AndroidViewModel {
    private LabelRepository repository;
    private LiveData<List<Label>> allLabels;

    public LabelViewModel(@NonNull Application application) {
        super(application);
        repository = new LabelRepository(application);
        allLabels = repository.getAllLabels();
    }

    public void insert(Label label){
        repository.insert(label);
    }

    public void update(Label label){
        repository.update(label);
    }

    public void delete(Label label){
        repository.delete(label);
    }

    public void deleteAllLabels(){
        repository.deleteAllLabels();
    }

    public LiveData<List<Label>> getAllLabels(){
        return allLabels;
    }

}
