package edu.ranken.emeier.hot5;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.ranken.emeier.hot5.data.NoteDatabase;
import edu.ranken.emeier.hot5.data.NoteRepository;
import edu.ranken.emeier.hot5.data.entity.Note;

public class NoteApp extends Application {

    // constants
    private static final String TAG = NoteApp.class.getSimpleName();

    private NoteDatabase mDatabase;
    private NoteRepository mRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        mDatabase = NoteDatabase.buildDatabase(this);
        mRepository = new NoteRepository(this, mDatabase);
    }

    public NoteRepository getRepository(){
        return mRepository;
    }
}
