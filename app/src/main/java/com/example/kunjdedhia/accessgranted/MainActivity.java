package com.example.kunjdedhia.accessgranted;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the View that shows the Multiple Choice category
        TextView multiple_choice = (TextView) findViewById(R.id.multiple_choice);

        // Set a click listener on that View
        multiple_choice.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the Multiple Choice category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the MultipleChoice Activity
                Intent multipleChoiceIntent = new Intent(MainActivity.this, com.example.android.fingerflow.MultipleChoiceActivity.class);

                // Start the new activity
                startActivity(multipleChoiceIntent);
            }
        });

        // Find the View that shows the morse code category
        TextView morse_code = (TextView) findViewById(R.id.morse_code);

        // Set a click listener on that View
        morse_code.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the Morse Code category is clicked.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the MorseCode activity
                Intent morseCodeIntent = new Intent(MainActivity.this, com.example.android.fingerflow.MorseCodeActivity.class);

                // Start the new activity
                startActivity(morseCodeIntent);
            }
        });

        // Find the View that shows the Game category
        TextView game = (TextView) findViewById(R.id.game);

        // Set a click listener on that View
        game.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the Multiple Choice category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the MultipleChoice Activity
                Intent gameIntent = new Intent(MainActivity.this, com.example.android.fingerflow.gameActivity.class);

                // Start the new activity
                startActivity(gameIntent);
            }
        });

        // Find the View that shows the Game category
        TextView multipurpose = (TextView) findViewById(R.id.multipurpose);

        // Set a click listener on that View
        multipurpose.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the Multiple Choice category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the MultipleChoice Activity
                Intent multipurposeIntent = new Intent(MainActivity.this, com.example.android.fingerflow.MultipurposeActivity.class);

                // Start the new activity
                startActivity(multipurposeIntent);
            }
        });


    }


}
