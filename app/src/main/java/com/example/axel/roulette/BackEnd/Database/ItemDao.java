package com.example.axel.roulette.BackEnd.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.axel.roulette.BackEnd.Entity.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);

    @Delete
    void delete(Item item);

    @Query("DELETE FROM game_log")
    void deleteAllItems();

    @Query("SELECT * FROM game_log ORDER BY id DESC")
    LiveData<List<Item>> getAllItems();
}
