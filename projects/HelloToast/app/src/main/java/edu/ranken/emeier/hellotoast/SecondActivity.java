package edu.ranken.emeier.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView countView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        countView = findViewById(R.id.displayCount);

        Intent intent = getIntent();
        String count = intent.getStringExtra(MainActivity.EXTRA_COUNT);
        countView.setText(count);
    }
}
