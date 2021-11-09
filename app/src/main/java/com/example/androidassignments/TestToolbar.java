package com.example.androidassignments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    private String OptionSnack = "You selected item 1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Clicked Floating Action Button", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public boolean onCreateOptionsMenu (Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem mi){
        int menuId = mi.getItemId();
        View view = findViewById(R.id.Toolbar);
        switch (menuId) {
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                Snackbar.make(view, OptionSnack, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                break;
            case R.id.action_two:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(TestToolbar.this);
                builder1.setTitle(R.string.DialogTitleToolbar)
                        .setPositiveButton(R.string.DialogPositive, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(TestToolbar.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.DialogNegative, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .show();
                break;
            case R.id.action_three:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(TestToolbar.this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogLayout = inflater.inflate(R.layout.toolbar_dialog, null);
                builder2.setView(dialogLayout)
                        .setTitle(R.string.ItemSnack)
                        .setPositiveButton(R.string.DialogPositive, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                OptionSnack = ((EditText) dialogLayout.findViewById(R.id.MessageField)).getText().toString();
                            }
                        })
                        .setNegativeButton(R.string.DialogNegative, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .show();
                break;
            case R.id.About:
                Log.d("Toolbar", "About selected");
                Toast toast = Toast.makeText(this, "Version 1.0, by Samuel Turschic", Toast.LENGTH_SHORT);
                toast.show();

                break;
        }
        return true;
    }
    @Override

    public void onBackPressed() {

        super.onBackPressed();

        setResult(Activity.RESULT_CANCELED);

        finish();

    }
}