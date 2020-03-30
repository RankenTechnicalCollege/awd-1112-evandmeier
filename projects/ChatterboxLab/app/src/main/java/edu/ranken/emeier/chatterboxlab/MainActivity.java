package edu.ranken.emeier.chatterboxlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.ranken.emeier.chatterboxlab.models.Message;

public class MainActivity extends AppCompatActivity {

    // constants
    private final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_USERNAME = "edu.ranken.emeier.chatterboxlab.EXTRA_USERNAME";

    // widgets
    private EditText mInputUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind widgets
        mInputUsername = findViewById(R.id.input_username);

        mInputUsername.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login();
                Log.d(TAG, "Logged in via DONE button!");
            }

            return false;
        });
    }

    public void onClick(View view) {
        login();
    }

    public void login() {
        if (mInputUsername.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter a username!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ChatroomActivity.class);
            intent.putExtra(EXTRA_USERNAME, mInputUsername.getText().toString());
            startActivity(intent);
        }
    }
}
