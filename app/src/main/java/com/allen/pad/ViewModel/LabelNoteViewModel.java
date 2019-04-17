package com.allen.pad.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.allen.pad.Database.LabelNote.LabelNoteRepository;
import com.allen.pad.Model.Label;
import com.allen.pad.Model.LabelNote;
import java.util.List;

public class LabelNoteViewModel extends AndroidViewModel {
    private LabelNoteRepository repository;
    private LiveData<List<LabelNote>> allLabels;

    public LabelNoteViewModel(@NonNull Application application) {
        super(application);
        repository = new LabelNoteRepository(application);
        allLabels = repository.getAllLabels();
    }

    public void insert(LabelNote labelNote){
        repository.insert(labelNote);
    }

    public void update(LabelNote labelNote){
        repository.update(labelNote);
    }

    public void delete(LabelNote labelNote){
        repository.delete(labelNote);
    }

    public void deleteAllLabels(){
        repository.deleteAllLabels();
    }

    public LiveData<List<LabelNote>> getAllLabels(){
        return allLabels;
    }

    public LiveData<List<LabelNote>> getNoteLabels(int id){
        return repository.getNoteLabels(id);
    }

    public LiveData<List<Label>> getLabels(int id){
        return repository.getLabels(id);
    }


}
