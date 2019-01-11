package com.example.axel.roulette.BackEnd.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "game_log")
public class Item implements Parcelable {

    @ColumnInfo(name = "title_text")
    public String text;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public int id;


    public Item(String text) {
        this.text = text;
    }

    public String getLogText() {
        return text;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeInt(this.id);
    }
    protected Item(Parcel in) {
        this.text = in.readString();
        this.id = in.readInt();
    }
    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }
        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
