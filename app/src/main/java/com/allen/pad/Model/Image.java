package com.allen.pad.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "image_table")
public class Image implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "primary_column")
    private String title;

    private boolean isChecked = false;

    @ColumnInfo(name = "note_id")
    private int note_id;

    public Image(String title, int note_id) {
        this.title = title;
        this.note_id = note_id;
    }

    protected Image(Parcel in) {
        id = in.readInt();
        title = in.readString();
        isChecked = in.readByte() != 0;
        note_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeInt(note_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
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
