package edu.ranken.emeier.hot5.data;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.ranken.emeier.hot5.NoteApp;
import edu.ranken.emeier.hot5.data.entity.Note;

public class NoteRepository {

    private static final String TAG = NoteRepository.class.getSimpleName();

    private NoteApp mApp;
    private NoteDatabase mDatabase;
    private LiveData<List<Note>> mNotes;

    public NoteRepository(NoteApp app, NoteDatabase db) {
        mApp = app;
        mDatabase = db;
        mNotes = db.getNoteDao().getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return mNotes;
    }

    public void insertNote(Note note){
        new InsertNoteTask().execute(note);
    }

    public void deleteAllNotes()  {
        new DeleteAllNotesTask().execute();
    }

    public void deleteNote(Note note)  {
        new DeleteNoteTask().execute(note);
    }

    public void updateNote(Note note) {
        new UpdateNoteTask().execute(note);
    }

    private class InsertNoteTask extends AsyncTask<Note, Void, Void> {

        @Override
        protected Void doInBackground(Note[] params) {
            mDatabase.getNoteDao().insert(params[0]);
            return null;
        }
    }

    private class DeleteAllNotesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void[] params) {
            mDatabase.getNoteDao().deleteAll();
            return null;
        }
    }

    private class DeleteNoteTask extends AsyncTask<Note, Void, Void> {

        @Override
        protected Void doInBackground(Note[] params) {
            mDatabase.getNoteDao().deleteNote(params[0]);
            return null;
        }
    }

    private class UpdateNoteTask extends AsyncTask<Note, Void, Void> {

        @Override
        protected Void doInBackground(Note[] params) {
            mDatabase.getNoteDao().update(params[0]);
            return null;
        }
    }
}
