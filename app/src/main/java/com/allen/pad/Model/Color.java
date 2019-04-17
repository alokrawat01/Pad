package com.allen.pad.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "color_table")
public class Color implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int color;

    private boolean selected;

    public Color(int color, boolean selected) {
        this.color = color;
        this.selected = selected;
    }

    protected Color(Parcel in) {
        id = in.readInt();
        color = in.readInt();
        selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(color);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Color> CREATOR = new Creator<Color>() {
        @Override
        public Color createFromParcel(Parcel in) {
            return new Color(in);
        }

        @Override
        public Color[] newArray(int size) {
            return new Color[size];
        }
    };

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
