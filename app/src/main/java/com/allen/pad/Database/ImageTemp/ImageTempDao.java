package com.allen.pad.Database.ImageTemp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.allen.pad.Model.ImageTemp;
import java.util.List;

@Dao
public interface ImageTempDao {

    @Insert
    void insert(ImageTemp imageTemp);

    @Update
    void update(ImageTemp imageTemp);

    @Delete
    void delete(ImageTemp imageTemp);

    @Query("DELETE FROM image_table_temp")
    void deleteAllImages();

    @Query("SELECT * FROM image_table_temp ORDER BY primary_column DESC")
    LiveData<List<ImageTemp>> getAllImages();

    @Query("SELECT * FROM image_table_temp WHERE note_id=:id ORDER BY primary_column DESC")
    LiveData<List<ImageTemp>> getNoteImages(int id);
}
