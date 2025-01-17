package edu.ranken.emeier.hellocompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mHelloTextView;
    private String[] mColorArray = { "red", "pink", "purple", "deep_purple", "indigo",
                                        "blue", "light_blue", "cyan", "teal", "green",
                                        "light_green", "lime", "yello", "amber", "orange",
                                        "deep_orange", "brown", "grey", "blue_grey", "black" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelloTextView = findViewById(R.id.hello_textview);

        // restore saved instance state  (the text color)
        if (savedInstanceState != null) {
            mHelloTextView.setTextColor(savedInstanceState.getInt("color"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("color", mHelloTextView.getCurrentTextColor());
    }

    public void changeColor(View view) {
        Random random = new Random();
        String colorName = mColorArray[random.nextInt(20)];

        int colorResourceName = getResources().getIdentifier(colorName, "color", getApplicationContext().getPackageName());
        int colorRes;

        // if the build version is greater or equal to Marshmallow (API 23)
        //ContextCompat.getColor(this, colorResourceName);
        if (Build.VERSION.SDK_INT >= 23) {
            colorRes = getColor(colorResourceName);
        } else {
            colorRes = getResources().getColor(colorResourceName);
        }

        mHelloTextView.setTextColor(colorRes);
    }
}
