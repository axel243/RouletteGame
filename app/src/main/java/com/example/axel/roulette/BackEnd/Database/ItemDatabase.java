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
                    .addCallback(roomCallback)
                    .build();

        }

        return sInstance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(sInstance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ItemDao itemDao;

        private PopulateDbAsyncTask(ItemDatabase db) {
            itemDao = db.itemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.insert(new Item("Title 1"));
            itemDao.insert(new Item("Title 2"));
            itemDao.insert(new Item("Title 3"));
            return null;
        }
    }

}
