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
    private LiveData<List<Note>> mALlNotes;
    private Note mNote;

    public NoteRepository(NoteApp app, NoteDatabase db) {
        mApp = app;
        mDatabase = db;
        mALlNotes = db.getNoteDao().getAllNotes();
    }

//❏ public LiveData<List<Note>> getAllNotes()
//❏ public Note getNoteById(int id)
//❏ public void insertNote(Note note)
//❏ public void updateNote(Note note)
//❏ public void deleteNote(int id)
//❏ public void deleteAllNotes()

    private Note getNote(int id) {
        mNote = mDatabase.getNoteDao().getNoteById(id);
        return mNote;
    }

    public LiveData<List<Note>> getAllNotes(){
        return mALlNotes;
    }

    public Note getNoteById(int id) {
        try {
            Note note = new GetNoteTask().execute(id).get();

            return note;
        } catch (Exception e) {
            return null;
        }
    }

    public void insertNote(Note note){
        new InsertNoteTask().execute(note);
    }

    public void updateNote(Note note) {
        new UpdateNoteTask().execute(note);
    }

    public void deleteNote(int id)  {
        new DeleteNoteTask().execute(id);
    }

    public void deleteAllNotes()  {
        new DeleteAllNotesTask().execute();
    }

    private class GetNoteTask extends AsyncTask<Integer, Void, Note> {

        @Override
        protected Note doInBackground(Integer[] params) {

            return mDatabase.getNoteDao().getNoteById(params[0]);
        }
    }

    private class InsertNoteTask extends AsyncTask<Note, Void, Void> {

        @Override
        protected Void doInBackground(Note[] params) {
            mDatabase.getNoteDao().insertNote(params[0]);
            return null;
        }
    }

    private class UpdateNoteTask extends AsyncTask<Note, Void, Void> {

        @Override
        protected Void doInBackground(Note[] params) {
            mDatabase.getNoteDao().updateNote(params[0]);
            return null;
        }
    }

    private class DeleteNoteTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer[] params) {
            mDatabase.getNoteDao().deleteNote(params[0]);
            return null;
        }
    }

    private class DeleteAllNotesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void[] params) {
            mDatabase.getNoteDao().deleteAllNotes();
            return null;
        }
    }
}
