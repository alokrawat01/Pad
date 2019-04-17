package com.allen.pad.Database.LabelTemp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.allen.pad.Model.LabelTemp;
import java.util.List;

@Dao
public interface LabelTempDao {

    @Insert
    void insert(LabelTemp labelTemp);

    @Update
    void update(LabelTemp labelTemp);

    @Delete
    void delete(LabelTemp labelTemp);

    @Query("DELETE FROM label_table_temp")
    void deleteAllLabel();

    @Query("SELECT * FROM label_table_temp ORDER BY primary_column DESC")
    LiveData<List<LabelTemp>> getAllLabel();

    @Query("SELECT * FROM label_table_temp WHERE note_id=:id ORDER BY primary_column DESC")
    LiveData<List<LabelTemp>> getNoteLabels(int id);
}
