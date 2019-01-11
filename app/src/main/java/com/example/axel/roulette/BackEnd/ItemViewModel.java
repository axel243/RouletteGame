package com.example.axel.roulette.BackEnd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.axel.roulette.BackEnd.Entity.Item;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository itemRepository;
    private LiveData<List<Item>> allItems;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
        allItems = itemRepository.getAllItems();
    }

    public void insert(Item item){
        itemRepository.insert(item);
    }

    public void delete(Item item){
        itemRepository.delete(item);
    }

    public void deleteAll(){
        itemRepository.deleteAllItems();
    }

    public LiveData<List<Item>> getAllItems(){
        return allItems;
    }


}
