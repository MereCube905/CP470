package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        list1 = findViewById(R.id.List1);
        txt1 = findViewById(R.id.EditTxt);
        sendBtn = findViewById(R.id.SendBtn);
        chatMsgs = new ArrayList<String>();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageAdapter = new ChatAdapter(ChatWindow.this);
                list1.setAdapter (messageAdapter);
                chatMsgs.add(txt1.getText().toString());
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()
                txt1.setText("");
            }
        });
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


}



