package edu.ranken.emeier.mytutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    // constants
    private final String TAG = "MainActivity";
    //private final String correctEmail = "admin@ranken.edu";
    private final String correctEmail = "";
    //private final String correctPassword = "P@ssw0rd";
    private final String correctPassword = "";

    // widgets
    private EditText mEmailInput, mPasswordInput;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "Created MainActivity");

        // get widgets
        mEmailInput = findViewById(R.id.input_email_address);
        mPasswordInput = findViewById(R.id.input_password);
        mLoginButton = findViewById(R.id.button_login);

        // listen for the 'DONE' key to be pressed
        mPasswordInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Log.d(TAG, "Done Button Clicked!");
                mLoginButton.performClick();
            }

            return false;
        });
    }

    public void login(View view) {
        Log.d(TAG, "Login Method Entered");

        // get values from input fields
        String emailAddress = mEmailInput.getText().toString();
        String password = mPasswordInput.getText().toString();

        if (!emailAddress.equals(correctEmail) || !password.equals(correctPassword)) {
            Log.d(TAG, "User failed login.");

            // display snackbar
            displaySnackbar(view, "Incorrect Email and/or Password");
        } else {
            Log.d(TAG, "User logged in.");

            // move to home activity
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    public static void displaySnackbar(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
