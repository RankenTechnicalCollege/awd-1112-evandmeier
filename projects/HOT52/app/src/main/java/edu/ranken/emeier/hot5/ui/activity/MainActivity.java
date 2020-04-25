package edu.ranken.emeier.hot5.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import edu.ranken.emeier.hot5.NoteApp;
import edu.ranken.emeier.hot5.R;
import edu.ranken.emeier.hot5.data.NoteRepository;
import edu.ranken.emeier.hot5.data.entity.Note;
import edu.ranken.emeier.hot5.ui.adapter.NoteListAdapter;
import edu.ranken.emeier.hot5.utils.Utils;

public class MainActivity extends AppCompatActivity {

    // constants
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_NEW_NOTE = 1;
    public static final int REQUEST_EDIT_NOTE = 2;

    // fields
    private NoteRepository mRepository;
    private LiveData<List<Note>> mNotes;
    private NoteListAdapter mAdapter;

    // widgets
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        // initialize fields
        NoteApp app = (NoteApp) getApplication();
        mRepository = app.getRepository();
        mNotes = mRepository.getAllNotes();
        mAdapter = new NoteListAdapter(this, mNotes.getValue());

        // bind widgets
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        // listen for data changes via LiveData and an Observer
        mNotes.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                mAdapter.setNotes(notes);
            }
        });

        // use ItemTouchHelper to setup swipe functionality
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.RIGHT
                ) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        // do nothing
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();

                        int noteId = mAdapter.getNoteIdAtPosition(position);
                        mRepository.deleteNote(noteId);
                    }
                }
        );

        touchHelper.attachToRecyclerView(mRecyclerView);
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
                // check if notes exist
                if (mNotes.getValue().size() > 0) {
                    AlertDialog alert = new AlertDialog.Builder(this)
                            .setCancelable(true)
                            .setMessage("Are you sure that you want to delete all notes?")
                            .setPositiveButton("Yes", (DialogInterface dialog, int which) -> {
                                mRepository.deleteAllNotes();
                            })
                            .setNegativeButton("No", (DialogInterface dialog, int which) -> {

                            })
                            .create();

                    alert.show();
                } else {
                    Toast.makeText(this, "You don't have any notes to delete!", Toast.LENGTH_LONG).show();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_NEW_NOTE && resultCode == RESULT_OK) {
           try {
               // get extras
               String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE_ADD);
               String date = data.getStringExtra(AddNoteActivity.EXTRA_DATE_ADD);
               String body = data.getStringExtra(AddNoteActivity.EXTRA_BODY_ADD);

               // create new Note object
               Note note = new Note(title, date, body);
               mRepository.insertNote(note);
           } catch (Exception e) {
               Utils.showToast(this, "There was an error when attempting to add your note.");
           }
        } else if (requestCode == REQUEST_EDIT_NOTE && resultCode == RESULT_OK) {
            // get extras
            int id = data.getIntExtra(EditNoteActivity.EXTRA_ID_EIDT, -2);

            if (id != -1) {
                Note note = mRepository.getNoteById(id);
                String title = data.getStringExtra(EditNoteActivity.EXTRA_TITLE_EDIT);
                String date = data.getStringExtra(EditNoteActivity.EXTRA_DATE_EDIT);
                String body = data.getStringExtra(EditNoteActivity.EXTRA_BODY_EDIT);

                note.setTitle(title);
                note.setDate(date);
                note.setBody(body);

                mRepository.updateNote(note);
            } else {
                Utils.showToast(this, "Failed to update the note!");
            }
        }
    }

    public void addNote(View view) {
        startActivityForResult(new Intent(this, AddNoteActivity.class), REQUEST_NEW_NOTE);
    }
}