package edu.ranken.emeier.homework44;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class DonutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);

        Toast.makeText(this, "You selected the Donut Screen", Toast.LENGTH_SHORT).show();
    }
}
