package com.example.android.fingerflow;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class gameActivity extends AppCompatActivity {
    View s0, s1, s2, m0, m1, m2, t0, t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Applying game scenario
        s0 = findViewById(R.id.s0);
        s2 = findViewById(R.id.s2);

        m0 = findViewById(R.id.m0);
        m2 = findViewById(R.id.m2);

        t0 = findViewById(R.id.t0);
        t2 = findViewById(R.id.t2);

        s0.setVisibility(View.INVISIBLE);
        s2.setVisibility(View.INVISIBLE);

        m0.setVisibility(View.INVISIBLE);
        m2.setVisibility(View.INVISIBLE);

        t0.setVisibility(View.INVISIBLE);


        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                t2.setVisibility(View.INVISIBLE);
                t0.setVisibility(View.VISIBLE);

            }
        }, 2000);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                m2.setVisibility(View.VISIBLE);
                t0.setVisibility(View.INVISIBLE);


            }
        }, 4000);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                m2.setVisibility(View.INVISIBLE);
                m0.setVisibility(View.VISIBLE);
                s2.setVisibility(View.VISIBLE);

            }
        }, 6000);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                m0.setVisibility(View.INVISIBLE);
                s0.setVisibility(View.VISIBLE);
                s2.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.VISIBLE);

            }
        }, 8000);
    }
}
