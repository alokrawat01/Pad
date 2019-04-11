package com.allen.pad.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "color_table")
public class Color {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int color;

    private boolean selected;

    public Color(int color, boolean selected) {
        this.color = color;
        this.selected = selected;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public boolean isSelected() {
        return selected;
    }
}
