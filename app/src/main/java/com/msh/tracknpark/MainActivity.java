package com.msh.tracknpark;

import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    View disabledButton13;
    View disabledButton14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disabledButton13= findViewById(R.id.ps13);
        disabledButton14= findViewById(R.id.ps14);
        disabledButton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disabledButton13Callback(v);
            }
        });
        disabledButton14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disabledButton14Callback(v);
            }
        });


    }

    void disabledButton13Callback(View view){
        disabledButton13.setBackgroundColor(255);
    }
    void disabledButton14Callback(View view){
        disabledButton14.setBackgroundColor(0);
    }
}
