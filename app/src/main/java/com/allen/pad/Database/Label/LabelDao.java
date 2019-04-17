package com.allen.pad.Database.Label;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.allen.pad.Model.Label;
import java.util.List;

@Dao
public interface LabelDao {

    @Insert
    void insert(Label label);

    @Update
    void update(Label label);

    @Delete
    void delete(Label label);

    @Query("DELETE FROM label_table")
    void deleteAllLabel();

    @Query("SELECT * FROM label_table ORDER BY primary_column DESC")
    LiveData<List<Label>> getAllLabel();

    @Query("SELECT * FROM label_table WHERE note_id=:id ORDER BY primary_column DESC")
    LiveData<List<Label>> getNoteLabels(int id);
}
