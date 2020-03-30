package edu.ranken.emeier.chatterboxlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import edu.ranken.emeier.chatterboxlab.adapters.MessageAdapter;
import edu.ranken.emeier.chatterboxlab.models.Message;

public class ChatroomActivity extends AppCompatActivity {

    // constants

    // widgets
    private RecyclerView mRecyclerView;

    // fields
    private MessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        // bind widgets
        mRecyclerView = findViewById(R.id.recyclerview);

        // get messages from API
        ChatterboxApp app = (ChatterboxApp) getApplication();
        ArrayList<Message> messages = app.getMessages();

        mAdapter = new MessageAdapter(this, messages);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(messages.size());

        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
    }
}
