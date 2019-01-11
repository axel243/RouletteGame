package com.example.axel.roulette.FrontEnd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.axel.roulette.R;

public class FactActivity extends AppCompatActivity {
    private TextView title;
    private TextView funFact;
    private Button closeFact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact);

        title = findViewById(R.id.title);
        funFact = findViewById(R.id.factText);
        closeFact = findViewById(R.id.close_button);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        closeFact.setOnClickListener(onClickListener);
    }
}
