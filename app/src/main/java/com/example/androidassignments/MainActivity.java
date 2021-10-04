package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity";
    Button MainButton;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        MainButton = findViewById(R.id.mainbutton);
        MainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 10);
            }
        });

    }
    protected  void onActivityResult(int requestCode, int responseCode, Intent data) {

        super.onActivityResult(requestCode, responseCode, data);
        if (requestCode == 10 && responseCode == Activity.RESULT_OK){
            Log.i(ACTIVITY_NAME, "Returned to MainActivity.onActivityResult");
            String messagePassed = data.getStringExtra("Response");
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this , messagePassed, duration);
            toast.show();

        }
    }
    protected void onResume() {

        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    protected void onStart() {

        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    protected void onPause() {

        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    protected void onStop() {

        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    protected void onDestroy() {

        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}