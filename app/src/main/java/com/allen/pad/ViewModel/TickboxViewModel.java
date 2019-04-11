package com.allen.pad.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.allen.pad.Database.Tickbox.TickboxRepository;
import com.allen.pad.Model.Tickbox;
import java.util.List;

public class TickboxViewModel extends AndroidViewModel {
    private TickboxRepository repository;
    private LiveData<List<Tickbox>> allTickboxs;

    public TickboxViewModel(@NonNull Application application) {
        super(application);
        repository = new TickboxRepository(application);
        allTickboxs = repository.getAllTickboxs();
    }

    public void insert(Tickbox tickbox){
        repository.insert(tickbox);
    }

    public void update(Tickbox tickbox){
        repository.update(tickbox);
    }

    public void delete(Tickbox tickbox){
        repository.delete(tickbox);
    }

    public void deleteAllTickboxs(){
        repository.deleteAllTickboxs();
    }

    public LiveData<List<Tickbox>> getAllTickboxs(){
        return allTickboxs;
    }

}
