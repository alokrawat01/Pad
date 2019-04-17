package com.allen.pad.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import com.allen.pad.Database.TimestampConverter;
import java.util.Date;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private int color;

    @ColumnInfo(name = "created_at")
    @TypeConverters({TimestampConverter.class})
    private Date createdAt;

    @ColumnInfo(name = "modified_at")
    @TypeConverters({TimestampConverter.class})
    private Date modifiedAt;

    @ColumnInfo(name = "primary_column")
    private int priority;


    public Note(String title, String description, int color, Date createdAt, Date modifiedAt, int priority) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public int getPriority() {
        return priority;
    }

}
