package edu.ranken.emeier.mytutor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private final String correctEmail = "admin@ranken.edu";
    private final String correctPassword = "P@ssw0rd";

    // widgets
    private EditText mEmailInput, mPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailInput = findViewById(R.id.input_email_address);
        mPasswordInput = findViewById(R.id.input_password);
    }

    public void login(View view) {
        // get values from input fields
        String emailAddress = mEmailInput.getText().toString();
        String password = mPasswordInput.getText().toString();

        if (!emailAddress.equals(correctEmail) || !password.equals(correctPassword)) {
            // display snackbar

        } else {
            // open HomeActivity
        }
    }
}
