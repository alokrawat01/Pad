package com.allen.pad.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "label_table")
public class Label {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "primary_column")
    private String title;

    private boolean isChecked = false;

    public Label(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
