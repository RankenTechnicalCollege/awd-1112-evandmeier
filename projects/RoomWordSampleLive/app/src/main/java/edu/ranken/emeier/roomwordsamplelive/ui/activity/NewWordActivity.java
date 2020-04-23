package edu.ranken.emeier.roomwordsamplelive.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import edu.ranken.emeier.roomwordsamplelive.R;
import edu.ranken.emeier.roomwordsamplelive.data.entitiy.Word;
import edu.ranken.emeier.roomwordsamplelive.ui.viewmodel.WordViewModel;

public class NewWordActivity extends AppCompatActivity {

    private static final String LOG_TAG = NewWordActivity.class.getSimpleName();
    public static final String EXTRA_WORD = "word";
    private EditText mEditText;
    private LiveData<List<Word>> mWords;
    private WordViewModel mViewModel;
    private Word mWord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        // bind widgets
        mEditText = findViewById(R.id.word_input);

        // get view model
        mViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        // get current list of words
        mWords = mViewModel.getAllWords();

        Intent intent = getIntent();
        if (intent.hasExtra("wordId") && intent.hasExtra("word")){
            int id = intent.getIntExtra("wordId", 0);
            String word = intent.getStringExtra("word");
            mWord = new Word(id, word);
        }
    }

    public void onSave (View view) {
        String input = mEditText.getText().toString();

        if (TextUtils.isEmpty(input)) {
            Toast.makeText(this, "Please enter a word", Toast.LENGTH_LONG).show();
        } else {
            // if we are updating the selected word
            if (mWord != null) {
                boolean goodToGo = true;
                for (Word word : mWords.getValue()) {
                    if (input.equals(word.getWord())) {
                        goodToGo = false;
                    }
                }

                if (goodToGo) {
                    mWord.setWord(input);
                    mViewModel.updateWord(mWord);

                    finish();
                } else {
                    Toast.makeText(this, "The word your inputted already exists!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_WORD, input);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        }
    }
}
