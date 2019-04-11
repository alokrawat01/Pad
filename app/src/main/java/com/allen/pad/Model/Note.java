package com.allen.pad.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.allen.pad.Database.StringConverter;
import com.allen.pad.Database.TimestampConverter;

import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    @TypeConverters({StringConverter.class})
    private ArrayList<String> label;

    @ColumnInfo(name = "created_at")
    @TypeConverters({TimestampConverter.class})
    private Date createdAt;

    @ColumnInfo(name = "modified_at")
    @TypeConverters({TimestampConverter.class})
    private Date modifiedAt;


    @ColumnInfo(name = "primary_column")
    private int priority;


    public Note(String title, String description, ArrayList<String> label, Date createdAt, Date modifiedAt, int priority) {
        this.title = title;
        this.description = description;
        this.label = label;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public ArrayList<String> getLabel() {
        return label;
    }

    public void setLabel(ArrayList<String> label) {
        this.label = label;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }
}
