package com.allen.pad.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "label_table")
public class Label implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "primary_column")
    private String title;

    private boolean isChecked = false;

    private int note_id;

    public Label(String title, int note_id) {
        this.title = title;
        this.note_id = note_id;
    }

    protected Label(Parcel in) {
        id = in.readInt();
        title = in.readString();
        isChecked = in.readByte() != 0;
        note_id = in.readInt();
    }

    public static final Creator<Label> CREATOR = new Creator<Label>() {
        @Override
        public Label createFromParcel(Parcel in) {
            return new Label(in);
        }

        @Override
        public Label[] newArray(int size) {
            return new Label[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeInt(note_id);
    }
}
