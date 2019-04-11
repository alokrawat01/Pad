package com.allen.pad.Database.Tickbox;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.allen.pad.Model.Tickbox;
import java.util.List;

@Dao
public interface TickboxDao {

    @Insert
    void insert(Tickbox tickbox);

    @Update
    void update(Tickbox tickbox);

    @Delete
    void delete(Tickbox tickbox);

    @Query("DELETE FROM tickbox_table")
    void deleteAllTickbox();

    @Query("SELECT * FROM tickbox_table ORDER BY primary_column DESC")
    LiveData<List<Tickbox>> getAllTickbox();
}
