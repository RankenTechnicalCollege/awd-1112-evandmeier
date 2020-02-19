package edu.ranken.emeier.homework44;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class FroyoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_froyo);

        Toast.makeText(this, "You selected the Froyo Screen", Toast.LENGTH_SHORT).show();
    }
}
