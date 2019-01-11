package com.example.axel.roulette.FrontEnd;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.axel.roulette.BackEnd.Entity.Item;
import com.example.axel.roulette.BackEnd.ItemViewModel;
import com.example.axel.roulette.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity{

    Button buttonBack;
    private List<Item> historylist;
    private Adapter mAdapter;
    RecyclerView historyRecyclerview;
    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buttonBack = findViewById(R.id.button_back_history_list);
        historyRecyclerview = findViewById(R.id.history_list_recyclerview);
        historyRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        historylist = new ArrayList<>();
        mAdapter = new Adapter(historylist);
        historyRecyclerview.setAdapter(mAdapter);

        itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                historylist = items;
                mAdapter.setItems(items);
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        buttonBack.setOnClickListener(onClickListener);
}

}
