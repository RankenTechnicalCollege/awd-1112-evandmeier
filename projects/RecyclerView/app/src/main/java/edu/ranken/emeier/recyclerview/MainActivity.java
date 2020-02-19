package edu.ranken.emeier.recyclerview;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mWordList;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get widgets
        mToolbar = findViewById(R.id.toolbar);
        mFab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recyclerview);

        // configure toolbar
        setSupportActionBar(mToolbar);

        // populate the word list
        resetWords();

        // configure fab
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = mWordList.size();
                mWordList.add("+ Word " + count);
                mAdapter.notifyItemInserted(count);
                mRecyclerView.smoothScrollToPosition(count);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("words", mWordList);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mWordList = savedInstanceState.getStringArrayList("words");
        mAdapter = new WordListAdapter(this, mWordList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            resetWords();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetWords() {
        mWordList = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            mWordList.add("Word " + i);
        }

        //mWordList = newList;

        mAdapter = new WordListAdapter(this, mWordList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
