package com.allen.pad.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "tickbox_table")
public class Tickbox implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "primary_column")
    private String title;

    private boolean isChecked = false;

    private int note_id;

    public Tickbox(String title) {
        this.title = title;
    }

    protected Tickbox(Parcel in) {
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

    public static final Creator<Tickbox> CREATOR = new Creator<Tickbox>() {
        @Override
        public Tickbox createFromParcel(Parcel in) {
            return new Tickbox(in);
        }

        @Override
        public Tickbox[] newArray(int size) {
            return new Tickbox[size];
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
