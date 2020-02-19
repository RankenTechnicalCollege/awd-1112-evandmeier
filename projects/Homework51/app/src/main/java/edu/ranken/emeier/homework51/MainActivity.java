package edu.ranken.emeier.homework51;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // holds the current level of the battery
    private int mLevel = 0;

    // widgets
    private Button mMinusButton, mPlusButton;
    private ImageView mBattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get widgets
        mMinusButton = findViewById(R.id.button_minus);
        mPlusButton = findViewById(R.id.button_plus);
        mBattery = findViewById(R.id.image_battery);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("current_level", mLevel);
        outState.putBoolean("minus_enabled", mMinusButton.isEnabled());
        outState.putBoolean("plus_enabled", mPlusButton.isEnabled());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the current level
        mLevel = savedInstanceState.getInt("current_level");
        mBattery.setImageLevel(mLevel);

        // disable buttons if necessary
        mMinusButton.setEnabled(savedInstanceState.getBoolean("minus_enabled"));
        mPlusButton.setEnabled(savedInstanceState.getBoolean("plus_enabled"));
    }

    public void increaseBattery(View view) {
        // increment level
        ++mLevel;
        mBattery.setImageLevel(mLevel);

        // if the minus button is disabled, enable it
        if (!mMinusButton.isEnabled()) {
            mMinusButton.setEnabled(true);
        }

        // if battery is at max level, disable it
        if (mLevel >= 6) {
            mPlusButton.setEnabled(false);
        }
    }

    public void decreaseBattery(View view) {
        // decrement level
        --mLevel;
        mBattery.setImageLevel(mLevel);

        // if hte plus button is disabled, enable it
        if (!mPlusButton.isEnabled()) {
            mPlusButton.setEnabled(true);
        }

        // if battery is at min level, disable it
        if (mLevel <= 0) {
            mMinusButton.setEnabled(false);
        }
    }
}
