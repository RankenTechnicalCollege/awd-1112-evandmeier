package edu.ranken.emeier.mytutor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.ranken.emeier.mytutor.utils.Article;
import edu.ranken.emeier.mytutor.utils.Utilities;

public class AddArticleActivity extends AppCompatActivity {

    // constants
    private final String TAG = "AddArticleActivity";
    public static String NEW_ARTICLE_EXTRA = "edu.ranken.emeier.mytutor.NewArticleExtra";

    // widgets
    private EditText mInputTitle, mInputAuthor, mInputDate, mInputLink;
    private Spinner mSpinner;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;

    // fields
    private String mTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        Log.d(TAG, "Entered AddArticleActivity");

        // get widgets
        mInputTitle = findViewById(R.id.input_article_title);
        mInputAuthor = findViewById(R.id.input_article_author);
        mInputDate = findViewById(R.id.input_published_date);
        mInputLink = findViewById(R.id.input_link);
        mSpinner = findViewById(R.id.topic_spinner);
        mToolbar = findViewById(R.id.toolbar);
        mFab = findViewById(R.id.fab);

        // configure toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setup spinner
        if (mSpinner != null) {
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // set topic to selected item
                    mTopic = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // do nothing
                }
            });

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_values, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mSpinner.setAdapter(adapter);
        }


        mFab.setOnClickListener((View view) -> {
            // check if the fields are
            if (mInputTitle.getText().toString().isEmpty() ||
                    mInputAuthor.getText().toString().isEmpty() ||
                    mInputLink.getText().toString().isEmpty() ||
                    mInputDate.getText().toString().isEmpty()) {

                Log.d(TAG, "Empty fields present. Showing alert.");

                // create alert dialog
                Utilities.showAlert(this, getString(R.string.alert_title), getString(R.string.alert_invalid_data));
            } else {
                // get the values of the fields
                String title = mInputTitle.getText().toString();
                String author = mInputAuthor.getText().toString();
                String date = mInputDate.getText().toString();
                String link = mInputLink.getText().toString();

                // check to make sure the link is a valid URL
                if (!URLUtil.isValidUrl(link)) {
                    Log.d(TAG, "Invalid URL entered. Showing alert.");

                    Utilities.showAlert(this, getString(R.string.alert_title), getString(R.string.alert_invalid_url));
                    return;
                }

                // convert date string to calendar object
                Calendar calendar = Utilities.parseDateString(date);

                // convert data into new Article object
                Article article = new Article(title, author, mTopic, link, calendar);
                Intent newIntent = new Intent();
                newIntent.putExtra(NEW_ARTICLE_EXTRA, article);

                setResult(RESULT_OK, newIntent);
                finish();
            }
        });

        Log.d(TAG, "AddArticleActivity successfully created.");
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.datepicker));
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string + "/" + day_string + "/" + year_string);

        mInputDate.setText(dateMessage);
    }
}