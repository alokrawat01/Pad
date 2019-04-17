package com.allen.pad.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "label_note_table")
public class LabelNote implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int label_id;

    private int note_id;

    public LabelNote(int label_id, int note_id) {
        this.label_id = label_id;
        this.note_id = note_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLabel_id() {
        return label_id;
    }

    public void setLabel_id(int label_id) {
        this.label_id = label_id;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    protected LabelNote(Parcel in) {
        id = in.readInt();
        label_id = in.readInt();
        note_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(label_id);
        dest.writeInt(note_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LabelNote> CREATOR = new Creator<LabelNote>() {
        @Override
        public LabelNote createFromParcel(Parcel in) {
            return new LabelNote(in);
        }

        @Override
        public LabelNote[] newArray(int size) {
            return new LabelNote[size];
        }
    };
}
