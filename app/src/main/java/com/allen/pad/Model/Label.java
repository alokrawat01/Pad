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

    private int note_id;

    public Label(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public int getNote_id() {
        return note_id;
    }
}
