package com.example.axel.roulette.BackEnd.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.axel.roulette.BackEnd.Entity.Item;

@Database(entities = {Item.class}, version = 1)
public abstract class ItemDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();


    private final static String NAME_DATABASE = "item_db";


    //Static instance

    private static ItemDatabase sInstance;


    public static ItemDatabase getInstance(Context context) {


        if(sInstance == null) {

            sInstance = Room.databaseBuilder(context.getApplicationContext(), ItemDatabase.class,   NAME_DATABASE)
                    .build();

        }

        return sInstance;

    }

}
