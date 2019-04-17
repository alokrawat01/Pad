package com.allen.pad.Database.Images;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.allen.pad.Model.Image;
import java.util.List;

@Dao
public interface ImageDao {

    @Insert
    void insert(Image image);

    @Update
    void update(Image image);

    @Delete
    void delete(Image image);

    @Query("DELETE FROM image_table")
    void deleteAllImages();

    @Query("SELECT * FROM image_table ORDER BY primary_column DESC")
    LiveData<List<Image>> getAllImages();

    @Query("SELECT * FROM image_table WHERE note_id=:id ORDER BY primary_column DESC")
    LiveData<List<Image>> getNoteImages(int id);
}
