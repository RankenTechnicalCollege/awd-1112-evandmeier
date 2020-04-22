package edu.ranken.emeier.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // widgets
    private TextView mTextView;

    // fields
    private WordListDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind widgets
        mTextView = findViewById(R.id.text_view);

        // initialize database
        mDatabase = new WordListDatabase(this);

        // retrieve words from database
        List<Word> words = mDatabase.getAllWords();
        StringBuilder output = new StringBuilder();
        for (Word word : words) {
            output.append(String.format("%d, %s, %s\n", word.getId(), word.getWord(), word.getDefinition()));
        }

        Word word = mDatabase.getWordById(2);
        if (word != null) {
            output.append("\nFound Word:\n");
            output.append(String.format("%d, %s, %s\n", word.getId(), word.getWord(), word.getDefinition()));
        } else {
            output.append("Word not found!");
        }

        mTextView.setText(output);
    }
}
