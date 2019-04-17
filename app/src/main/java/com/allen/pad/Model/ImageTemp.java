package com.allen.pad.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "image_table_temp")
public class ImageTemp implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "primary_column")
    private String title;

    @ColumnInfo(name = "note_id")
    private int note_id;

    public ImageTemp(String title, int note_id) {
        this.title = title;
        this.note_id = note_id;
    }

    protected ImageTemp(Parcel in) {
        id = in.readInt();
        title = in.readString();
        note_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(note_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageTemp> CREATOR = new Creator<ImageTemp>() {
        @Override
        public ImageTemp createFromParcel(Parcel in) {
            return new ImageTemp(in);
        }

        @Override
        public ImageTemp[] newArray(int size) {
            return new ImageTemp[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getNote_id() {
        return note_id;
    }
}
