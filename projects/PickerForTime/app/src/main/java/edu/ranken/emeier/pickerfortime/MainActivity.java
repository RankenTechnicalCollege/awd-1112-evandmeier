package edu.ranken.emeier.pickerfortime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showTimePicker(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.timepicker));
    }

    public void processTimePickerResult(int hourOfDay, int minute) {
        // convert time elements into strings
        //String hour_string = Integer.toString(hourOfDay);
        //String minute_string = Integer.toString(minute);
        //String timeMessage = (hour_string + ":" + minute_string);

        Toast.makeText(this, getString(R.string.time_toast, hourOfDay, minute), Toast.LENGTH_SHORT).show();
    }
}