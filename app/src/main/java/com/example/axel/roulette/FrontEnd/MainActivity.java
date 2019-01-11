package com.example.axel.roulette.FrontEnd;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.axel.roulette.BackEnd.Entity.Item;
import com.example.axel.roulette.BackEnd.ItemViewModel;
import com.example.axel.roulette.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    boolean blnButtonRotation = true;
    int intNumber = 6;
    long lngDegrees = 0;
    SharedPreferences sharedPreferences;
    private Adapter mAdapter;
    private List<Item> logitems;
    RecyclerView log;
    ImageView selected,imageRoulette;
    Button b_start,b_up,b_down,buttonToHistory;
    private ItemViewModel itemViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_start = findViewById(com.example.axel.roulette.R.id.buttonStart);
        b_up = findViewById(com.example.axel.roulette.R.id.buttonUp);
        b_down = findViewById(com.example.axel.roulette.R.id.buttonDown);
        buttonToHistory= findViewById(R.id.go_to_number_list_button);
        selected = findViewById(com.example.axel.roulette.R.id.imageSelected);
        imageRoulette = findViewById(com.example.axel.roulette.R.id.rouletteImage);
        log = findViewById(R.id.recyclerview);
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        logitems = new ArrayList<>();
        mAdapter = new Adapter(logitems);
        log.setAdapter(mAdapter);
        log.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.intNumber = this.sharedPreferences.getInt("INT_NUMBER",6);
        setImageRoulette(this.intNumber);

        itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {

            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =  new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        int position = (viewHolder.getAdapterPosition());
                        logitems.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(log);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(myIntent); }
        };
        buttonToHistory.setOnClickListener(onClickListener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onAnimationStart(Animation animation) {
        this.blnButtonRotation = false;
        b_start.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Toast toast = Toast.makeText(this, " The number is " + String.valueOf((int)(((double)this.intNumber)
                - Math.floor(((double)this.lngDegrees) / (360.0d / ((double)this.intNumber))))) + " ",Toast.LENGTH_SHORT);
        toast.setGravity(49,0,0);
        toast.show();
        this.blnButtonRotation = true;
        b_start.setVisibility(View.VISIBLE);
        String text = "You threw " + String.valueOf((int)(((double)this.intNumber)
                - Math.floor(((double)this.lngDegrees) / (360.0d / ((double)this.intNumber))))) + " Click here to see a fun fact about this number";
        Item newItem = new Item(text);
        logitems.add(0, newItem);
        itemViewModel.insert(newItem);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void onClickButtonRotation(View v) {
        if(this.blnButtonRotation){
            int ran = new Random().nextInt(360) + 3600;
            RotateAnimation rotateAnimation = new RotateAnimation((float)this.lngDegrees, (float)
                    (this.lngDegrees + ((long)ran)),1,0.5f,1,0.5f);

            this.lngDegrees = (this.lngDegrees + ((long)ran)) % 360;
            rotateAnimation.setDuration((long)ran);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setInterpolator(new DecelerateInterpolator());
            rotateAnimation.setAnimationListener(this);
            imageRoulette.setAnimation(rotateAnimation);
            imageRoulette.startAnimation(rotateAnimation);
        }
    }

    public void buttonUp(View v) {
        if(this.intNumber < 10){
            this.intNumber++;
            setImageRoulette(this.intNumber);
            b_down.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor = this.sharedPreferences.edit();
            editor.putInt("INT_NUMBER",this.intNumber);
            editor.apply();
        }
        if(this.intNumber ==10){
            b_up.setVisibility(View.INVISIBLE);
        }
    }


    public void buttonDown(View v) {
        if(this.intNumber > 2) {
            intNumber--;
            setImageRoulette(this.intNumber);
            b_up.setVisibility(View.VISIBLE);

            SharedPreferences.Editor editor = this.sharedPreferences.edit();
            editor.putInt("INT_NUMBER",this.intNumber);
            editor.apply();
        }
        if(this.intNumber < 2) {
            b_down.setVisibility(View.INVISIBLE);
        }
    }

    private void setImageRoulette(int myNumber) {
        switch(myNumber) {
            case 1:
                imageRoulette.setImageDrawable(getResources().getDrawable(com.example.axel.roulette.R.drawable.roulette));
                return;
            case 2:
                imageRoulette.setImageDrawable(getResources().getDrawable(com.example.axel.roulette.R.drawable.roulette_2));
                return;
            case 3:
                imageRoulette.setImageDrawable(getResources().getDrawable(com.example.axel.roulette.R.drawable.roulette_3));
                return;
            case 4:
                imageRoulette.setImageDrawable(getResources().getDrawable(com.example.axel.roulette.R.drawable.roulette_4));
                return;
            case 5:
                imageRoulette.setImageDrawable(getResources().getDrawable(com.example.axel.roulette.R.drawable.roulette_5));
                return;
            case 6:
                imageRoulette.setImageDrawable(getResources().getDrawable(com.example.axel.roulette.R.drawable.roulette_6));
                return;
            case 7:
                imageRoulette.setImageDrawable(getResources().getDrawable(com.example.axel.roulette.R.drawable.roulette_7));
                return;
            case 8:
                imageRoulette.setImageDrawable(getResources().getDrawable(com.example.axel.roulette.R.drawable.roulette_8));
                return;
            case 9:
                imageRoulette.setImageDrawable(getResources().getDrawable(com.example.axel.roulette.R.drawable.roulette_9));
                return;
            case 10:
                imageRoulette.setImageDrawable(getResources().getDrawable(com.example.axel.roulette.R.drawable.roulette_10));
        }
    }
}
