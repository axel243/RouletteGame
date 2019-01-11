package com.example.axel.roulette.BackEnd;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.axel.roulette.BackEnd.Database.ItemDao;
import com.example.axel.roulette.BackEnd.Database.ItemDatabase;
import com.example.axel.roulette.BackEnd.Entity.Item;

import java.util.List;

public class ItemRepository {
    private ItemDao itemDao;
    private LiveData<List<Item>> allItems;

    public ItemRepository(Application application) {
        ItemDatabase database = ItemDatabase.getInstance(application);
        itemDao = database.itemDao();
        allItems = itemDao.getAllItems();
    }

    public void insert(Item item){
        new InsertItemAsyncTask(itemDao).execute(item);
    }

    public void delete(Item item){
        new DeleteItemAsyncTask(itemDao).execute(item);
    }

    public void deleteAllItems(){
        new DeleteAllItemsAsyncTask(itemDao).execute();
    }

    public LiveData<List<Item>> getAllItems(){
        return allItems;
    }

    private static class InsertItemAsyncTask extends AsyncTask<Item,Void,Void> {

        private ItemDao itemDao;

        private InsertItemAsyncTask(ItemDao itemDao){
            this.itemDao = itemDao;
        }


        @Override
        protected Void doInBackground(Item... items) {
            itemDao.insert(items[0]);
            return null;
        }
    }

    private static class DeleteItemAsyncTask extends AsyncTask<Item,Void,Void> {

        private ItemDao itemDao;

        private DeleteItemAsyncTask(ItemDao itemDao){
            this.itemDao = itemDao;
        }


        @Override
        protected Void doInBackground(Item... items) {
            itemDao.delete(items[0]);
            return null;
        }
    }

    private static class DeleteAllItemsAsyncTask extends AsyncTask<Void,Void,Void> {

        private ItemDao itemDao;

        private DeleteAllItemsAsyncTask(ItemDao itemDao){
            this.itemDao = itemDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.deleteAllItems();
            return null;
        }
    }

}
