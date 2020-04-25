package edu.ranken.emeier.hot5.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import edu.ranken.emeier.hot5.NoteApp;
import edu.ranken.emeier.hot5.R;
import edu.ranken.emeier.hot5.data.NoteRepository;
import edu.ranken.emeier.hot5.data.entity.Note;
import edu.ranken.emeier.hot5.ui.adapter.NoteListAdapter;
import edu.ranken.emeier.hot5.ui.fragment.DatePickerFragment;
import edu.ranken.emeier.hot5.utils.Utils;

public class EditNoteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // constants
    public static final String TAG = EditNoteActivity.class.getSimpleName();
    public static final String EXTRA_ID_EIDT = "edu.ranken.emeier.hot5.EXTRA_ID_EDIT";
    public static final String EXTRA_TITLE_EDIT = "edu.ranken.emeier.hot5.EXTRA_TITLE_EDIT";
    public static final String EXTRA_DATE_EDIT = "edu.ranken.emeier.hot5.EXTRA_DATE_EDIT";
    public static final String EXTRA_BODY_EDIT = "edu.ranken.emeier.hot5.EXTRA_BODY_EDIT";

    // fields
    private NoteRepository mRepository;
    private int mNoteId;

    // widgets
    private EditText mInputTitle, mInputDate, mInputBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        // configure toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get repository
        NoteApp app = (NoteApp) getApplication();
        mRepository = app.getRepository();

        // bind widgets
        mInputTitle = findViewById(R.id.input_title);
        mInputDate = findViewById(R.id.input_date);
        mInputBody = findViewById(R.id.input_body);
        ImageButton mDatePicker = findViewById(R.id.datePickerButton);

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

        // receive the Note Id from the intent
        Intent intent = getIntent();
        mNoteId = intent.getIntExtra(NoteListAdapter.EXTRA_NOTE_ID, -1);

        if (mNoteId != -1) {
            Note note = mRepository.getNoteById(mNoteId);

            mInputTitle.setText(note.getTitle());
            mInputDate.setText(note.getDate());
            mInputBody.setText(note.getBody());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                AlertDialog alert = new AlertDialog.Builder(this)
                        .setCancelable(true)
                        .setMessage("Are you sure that you want to delete this note?")
                        .setPositiveButton("Yes", (DialogInterface dialog, int which) -> {
                            mRepository.deleteNote(mNoteId);
                            finish();
                        })
                        .setNegativeButton("No", (DialogInterface dialog, int which) -> {

                        })
                        .create();

                alert.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String title = mInputTitle.getText().toString();
        String date = mInputDate.getText().toString();
        String body = mInputBody.getText().toString();

        outState.putString(EXTRA_TITLE_EDIT, title);
        outState.putString(EXTRA_DATE_EDIT, date);
        outState.putString(EXTRA_BODY_EDIT, body);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mInputTitle.setText(savedInstanceState.getString(EXTRA_TITLE_EDIT));
        mInputDate.setText(savedInstanceState.getString(EXTRA_DATE_EDIT));
        mInputBody.setText(savedInstanceState.getString(EXTRA_BODY_EDIT));
    }

    public void editNote(View view) {
        String title = mInputTitle.getText().toString();
        String date = mInputDate.getText().toString();
        String body = mInputBody.getText().toString();

        if (TextUtils.isEmpty(title) ||
                TextUtils.isEmpty(date) ||
                TextUtils.isEmpty(body)) {
            Utils.showToast(this, "Make sure you have values for each field");
        } else {
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_ID_EIDT, mNoteId);
            replyIntent.putExtra(EXTRA_TITLE_EDIT, title);
            replyIntent.putExtra(EXTRA_DATE_EDIT, date);
            replyIntent.putExtra(EXTRA_BODY_EDIT, body);

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
        newFragment.show(getSupportFragmentManager(), "datePickerEdit");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        processDatePickerResult(year, month, dayOfMonth);
    }
}