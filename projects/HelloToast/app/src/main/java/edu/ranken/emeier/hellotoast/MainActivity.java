package edu.ranken.emeier.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_COUNT = "edu.ranken.emeier.hellotoast.COUNT_EXTRA";

    private int mCount = 0;
    private TextView mShowCount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = findViewById(R.id.show_count);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Add information for saving HelloToast counter
        // to the outState bundle
        String count = Integer.toString(mCount);
        outState.putString("count", count);

        //outState.putInt("count", count);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String count = savedInstanceState.getString("count");
        mCount = Integer.parseInt(count);

        //mCount = savedInstanceState.getString("count", 0);

        if (mShowCount != null) {
            mShowCount.setText(count);
            //mShowCount.setText(Integer.toString(mCount));
        }
    }

    public void showToast(View view) {
//        Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
//        toast.show();

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_COUNT, Integer.toString(mCount));
        startActivity(intent);
    }

    public void countUp(View view) {
        ++mCount;

        if (mShowCount != null) {
            mShowCount.setText(Integer.toString(mCount));
        }
    }
}