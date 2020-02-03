package edu.ranken.emeier.counterhomework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Holds the current ocunt
    private static int mCount = 0;

    // Holds the count TextView
    private TextView mCountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCountView = findViewById(R.id.text_count);
        mCountView.setText(Integer.toString(mCount));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("current_count", mCount);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (mCountView != null) {
            int count = savedInstanceState.getInt("current_count");

            mCountView.setText(Integer.toString(count));
        }
    }

    public void incrementCount(View view) {
        ++mCount;

        if (mCountView != null) {
            mCountView.setText(Integer.toString(mCount));
        }
    }
}