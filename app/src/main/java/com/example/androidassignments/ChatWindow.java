package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
public class ChatWindow extends AppCompatActivity {
    ListView list1;
    EditText txt1;
    Button sendBtn;
    ArrayList<String> chatMsgs;
    ChatAdapter messageAdapter;
    protected static SQLiteDatabase db;
    protected static final String ChatWindow = "ChatWindow";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        messageAdapter = new ChatAdapter(ChatWindow.this);
        list1 = findViewById(R.id.List1);
        txt1 = findViewById(R.id.EditTxt);
        sendBtn = findViewById(R.id.SendBtn);
        chatMsgs = new ArrayList<String>();
        ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        final Cursor cursor = db.rawQuery("SELECT KEY_MESSAGE FROM Messages;", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.i(ChatWindow, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndexOrThrow(ChatDatabaseHelper.KEY_MESSAGE)));
            String message = cursor.getString(cursor.getColumnIndexOrThrow(ChatDatabaseHelper.KEY_MESSAGE));
            chatMsgs.add(message);
            cursor.moveToNext();

        }
        Log.i(ChatWindow, "Cursor’s  column count =" + cursor.getColumnCount() );
        for (int i = 0; i < cursor.getColumnCount(); i++){
            Log.i(ChatWindow, cursor.getColumnName(i));
        }

        list1.setAdapter (messageAdapter);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chatMsgs.add(txt1.getText().toString());
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()

                db.execSQL("INSERT INTO Messages(KEY_MESSAGE) VALUES('" + txt1.getText().toString() + "')");
                txt1.setText("");
            }
        });
        Log.i(ChatWindow, "In onCreate()");



    }
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        Log.i(ChatWindow, "In onDestroy()");
    }
    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount(){
            return (chatMsgs.size());
        }
        public String getItem(int position){
            return (chatMsgs.get(position));
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;

        }
    }
    @Override

    public void onBackPressed() {

        super.onBackPressed();

        setResult(Activity.RESULT_CANCELED);

        finish();

    }


}



