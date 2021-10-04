package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    Button LoginButton;
    EditText EmailField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        LoginButton = findViewById(R.id.Button);
        EmailField = findViewById(R.id.email);
        SharedPreferences prefs = getSharedPreferences("LoginActivity", Context.MODE_PRIVATE);
        EmailField.setText(prefs.getString("DefaultEmail", "email@domain.com"));
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("DefaultEmail", String.valueOf(EmailField.getText()));
                EmailField.setText(prefs.getString("DefaultEmail", " "));
                edit.commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
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