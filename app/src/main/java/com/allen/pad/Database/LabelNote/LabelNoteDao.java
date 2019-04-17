package com.allen.pad.Database.LabelNote;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.allen.pad.Model.LabelNote;
import java.util.List;

@Dao
public interface LabelNoteDao {

    @Insert
    void insert(LabelNote labelNote);

    @Update
    void update(LabelNote labelNote);

    @Delete
    void delete(LabelNote labelNote);

    @Query("DELETE FROM label_note_table")
    void deleteAllLabel();

    @Query("SELECT * FROM label_note_table ORDER BY id DESC")
    LiveData<List<LabelNote>> getAllLabel();

    @Query("SELECT * FROM label_note_table WHERE note_id=:id ORDER BY id DESC")
    LiveData<List<LabelNote>> getNoteLabels(int id);

    @Query("SELECT * FROM label_table WHERE id=:id ORDER BY id DESC")
    LiveData<List<LabelNote>> getNoteLabel(int id);

}
