package edu.ranken.emeier.roomwordsamplelive.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import edu.ranken.emeier.roomwordsamplelive.R;
import edu.ranken.emeier.roomwordsamplelive.data.WordRepository;
import edu.ranken.emeier.roomwordsamplelive.data.entitiy.Word;
import edu.ranken.emeier.roomwordsamplelive.ui.adapter.WordListAdapter;
import edu.ranken.emeier.roomwordsamplelive.ui.viewmodel.WordViewModel;

public class MainActivity extends AppCompatActivity {

    // constants
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_NEW_WORD = 1;

    // fields
    private WordViewModel mWordViewModel;
    private RecyclerView mRecyclerView;
    private LiveData<List<Word>> mWords;
    private WordListAdapter mAdapter;
    private LocalBroadcastManager mBroadcastManager;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (WordRepository.ACTION_ERROR.equals(action)) {
                String message = intent.getStringExtra(WordRepository.EXTRA_MESSAGE);
                displayToast(message);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWords = mWordViewModel.getAllWords();
        mBroadcastManager = LocalBroadcastManager.getInstance(this);

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new WordListAdapter(this, mWords.getValue());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mWords.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                mAdapter.setItems(words);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback
                        (0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove
                            (RecyclerView recyclerView,
                             RecyclerView.ViewHolder viewHolder,
                             RecyclerView.ViewHolder target) {

                        // do nothing
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();

                        Word word = mAdapter.getWordAtPosition(position);

                        // Delete the word
                        mWordViewModel.deleteWord(word);
                    }
                });

        helper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WordRepository.ACTION_ERROR);
        mBroadcastManager.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        mBroadcastManager.unregisterReceiver(mReceiver);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_data:
                Toast.makeText(this, "Clearing the data...",
                        Toast.LENGTH_SHORT).show();

                mWordViewModel.deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void insertWord(View view) {
        startActivityForResult(new Intent(this, NewWordActivity.class), REQUEST_NEW_WORD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NEW_WORD && resultCode == RESULT_OK) {
            String word = data.getStringExtra(NewWordActivity.EXTRA_WORD);
            Toast.makeText(this, word, Toast.LENGTH_LONG).show();

            mWordViewModel.insertWord(new Word(word));
        }
    }

    public void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}