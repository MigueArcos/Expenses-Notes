package com.migue.zeus.expensesnotes.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private int papu;

    protected Note(Parcel in) {
        papu = in.readInt();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(papu);
    }
}
