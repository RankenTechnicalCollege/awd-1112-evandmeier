package edu.ranken.emeier.helloconstraint;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    private TextView mShowCount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShowCount = findViewById(R.id.show_count);
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp(View view) {
        ++mCount;

        if (mShowCount != null) {
            mShowCount.setText(Integer.toString(mCount));
        }

        if (mCount % 2 == 0) {
            view.setBackgroundTintList(getResources().getColorStateList(R.color.color_red));
        } else {
            view.setBackgroundTintList(getResources().getColorStateList(R.color.color_blue));
        }

        Button zeroButton = findViewById(R.id.button_zero);
        zeroButton.setBackgroundTintList(zeroButton.getResources().getColorStateList(R.color.colorAccent));
    }

    public void resetCount(View view) {
        mCount = 0;

        view.setBackgroundTintList(getResources().getColorStateList(R.color.color_grey));

        Button countButton = findViewById(R.id.button_count);
        countButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));

        if (mShowCount != null) {
            mShowCount.setText(Integer.toString(mCount));
        }
    }
}
