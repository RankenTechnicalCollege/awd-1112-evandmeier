package edu.ranken.emeier.hot5.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;
import java.util.Locale;

import edu.ranken.emeier.hot5.R;
import edu.ranken.emeier.hot5.data.entity.Note;
import edu.ranken.emeier.hot5.ui.fragment.DatePickerFragment;
import edu.ranken.emeier.hot5.utils.Utils;

public class AddNoteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // constants
    private static final String TAG = AddNoteActivity.class.getSimpleName();
    public static final String EXTRA_TITLE_ADD = "edu.ranken.emeier.hot5.EXTRA_TITLE_ADD";
    public static final String EXTRA_DATE_ADD = "edu.ranken.emeier.hot5.EXTRA_DATE_ADD";
    public static final String EXTRA_BODY_ADD = "edu.ranken.emeier.hot5.EXTRA_BODY_ADD";

    // widgets
    private EditText mInputTitle, mInputDate, mInputBody;
    private ImageButton mDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // configure toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // bind widgets
        mInputTitle = findViewById(R.id.input_title);
        mInputDate = findViewById(R.id.input_date);
        mInputBody = findViewById(R.id.input_body);
        mDatePicker = findViewById(R.id.datePickerButton);

        mDatePicker.setOnClickListener((View v) -> {
            DialogFragment newFragment = new DatePickerFragment(this);
            newFragment.show(getSupportFragmentManager(), "datePicker");
        });

        // get the current date to set the default
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        processDatePickerResult(year, month, day);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String title = mInputTitle.getText().toString();
        String date = mInputDate.getText().toString();
        String body = mInputBody.getText().toString();

        outState.putString(EXTRA_TITLE_ADD, title);
        outState.putString(EXTRA_DATE_ADD, date);
        outState.putString(EXTRA_BODY_ADD, body);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mInputTitle.setText(savedInstanceState.getString(EXTRA_TITLE_ADD));
        mInputDate.setText(savedInstanceState.getString(EXTRA_DATE_ADD));
        mInputBody.setText(savedInstanceState.getString(EXTRA_BODY_ADD));
    }

    public void saveNote(View view) {
        String title = mInputTitle.getText().toString();
        String date = mInputDate.getText().toString();
        String body = mInputBody.getText().toString();

        if (TextUtils.isEmpty(title) ||
                TextUtils.isEmpty(date) ||
                TextUtils.isEmpty(body)) {
            Utils.showToast(this, "Make sure you have values for each field");
        } else {
            // create the reply intent and add extras
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_TITLE_ADD, title);
            replyIntent.putExtra(EXTRA_DATE_ADD, date);
            replyIntent.putExtra(EXTRA_BODY_ADD, body);

            // set the result of the intent and finish the activity
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

    public void processDatePickerResult(int year, int month, int day) {
        String dateString = String.format(Locale.US, "%d/%d/%d", month + 1, day, year);
        mInputDate.setText(dateString);
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(getSupportFragmentManager(), "datePickerAdd");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        processDatePickerResult(year, month, dayOfMonth);
    }
}
