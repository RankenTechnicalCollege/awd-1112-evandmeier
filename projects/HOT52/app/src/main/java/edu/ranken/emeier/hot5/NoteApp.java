package edu.ranken.emeier.hot5;

import android.app.Application;

import edu.ranken.emeier.hot5.data.NoteDatabase;
import edu.ranken.emeier.hot5.data.NoteRepository;

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
