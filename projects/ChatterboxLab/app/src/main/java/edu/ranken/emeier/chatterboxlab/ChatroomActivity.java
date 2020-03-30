package edu.ranken.emeier.chatterboxlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ranken.emeier.chatterboxlab.adapters.MessageAdapter;
import edu.ranken.emeier.chatterboxlab.models.Message;

public class ChatroomActivity extends AppCompatActivity {

    // constants
    private final String TAG = ChatroomActivity.class.getSimpleName();
    public static final String GET_URL = "http://iwt.ranken.edu/ExampleWebServices/Chaterbox/GetMessages";
    public static final String POST_URL = "http://iwt.ranken.edu/ExampleWebServices/Chaterbox/PostMessage";

    // widgets
    private RecyclerView mRecyclerView;
    private EditText mInputMessage;

    // fields
    private RequestQueue mRequestQueue;
    private MessageAdapter mAdapter;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        // bind widgets
        mRecyclerView = findViewById(R.id.recyclerview);
        mInputMessage = findViewById(R.id.input_message);

        mInputMessage.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendMessage();
                Log.d(TAG, "Sent message via DONE button!");
            }

            return false;
        });

        // setup RequestQueue
        mRequestQueue = Volley.newRequestQueue(this);

        // populate the RecyclerView with the current list of messages
        getMessages();

        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // end the RequestQueue
        mRequestQueue.stop();
    }

    public void onClick(View view) {
        sendMessage();
        hideSoftKeyboard();
        Log.d(TAG, "Sent message via 'Send' Button!");
    }

    public void sendMessage() {
        String message = mInputMessage.getText().toString();

        if (message.isEmpty()) {
            // error
            Toast.makeText(this, "Cannot send empty messages!", Toast.LENGTH_SHORT).show();
        } else {
            postMessage(username, message);
            mInputMessage.setText("");
            mInputMessage.setHint(R.string.text_hint);
        }
    }

    public void getMessages() {
        ArrayList<Message> messages = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                GET_URL,
                null,
                (JSONArray response) -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            Message message = new Message(
                                    response.getJSONObject(i).getString("user"),
                                    response.getJSONObject(i).getString("msg"),
                                    response.getJSONObject(i).getString("time")
                            );

                            Log.d(TAG, message.getUser());

                            // add message to list
                            messages.add(message);
                        } catch (JSONException e) {
                            Log.e(TAG, String.format("Failed to parse Message at index '%d'.", i));
                        }
                    }

                    // create adapter with the updated list of messages
                    mAdapter = new MessageAdapter(this, messages);

                    // setup RecyclerView with the MessageAdapter
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.smoothScrollToPosition(messages.size());
                },
                (VolleyError error) -> {
                    Toast.makeText(this, "Failed to get messages!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to parse JSONArray.");
                }
        );

        mRequestQueue.add(request);
    }

    public void postMessage(String username, String message) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                POST_URL,
                (String response) -> {
                    Toast.makeText(this, "Successfully sent message!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, String.format("%s posted '%s'", username, message));

                    // update the screen with the updated list of messages
                    getMessages();
                },
                (VolleyError error) -> {
                    Toast.makeText(this, "Failed to send message!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to send message! VOLLEY");
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("user", username);
                params.put("message", message);
                return params;
            }
        };

        mRequestQueue.add(request);
    }

    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
