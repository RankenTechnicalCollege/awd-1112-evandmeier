package edu.ranken.emeier.roomwordsamplelive.data;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.List;

import edu.ranken.emeier.roomwordsamplelive.data.entitiy.Word;

public class WordRepository {

    // constants
    private static final String LOG_TAG = WordRepository.class.getSimpleName();
    public static final String ACTION_ERROR = "edu.ranken.emeier.roomwordsamplelive.ERROR";
    public static final String EXTRA_MESSAGE = "message";

    // widgets
    private WordApp mApp;
    private WordDatabase mDatabase;
    private LiveData<List<Word>> mAllWords;
    private LocalBroadcastManager mBroadcastManager;

    public WordRepository(WordApp app, WordDatabase db){
        mApp = app;
        mDatabase = db;
        mAllWords = db.getWordDao().getAllWords();
        mBroadcastManager = LocalBroadcastManager.getInstance(app);
    }

    public LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

    public void insertWord(Word word){
        new InsertWordTask().execute(word);
    }

    public void deleteAllWords()  {
        new DeleteAllWordsTask().execute();
    }

    public void deleteWord(Word word)  {
        new DeleteWordTask().execute(word);
    }

    public void updateWord(Word word) {
        new UpdateWordTask().execute(word);
    }

    private class InsertWordTask extends AsyncTask<Word,Void, Void> {

        @Override
        protected Void doInBackground(Word[] params) {
            try {
                Word word = params[0];
                mDatabase.getWordDao().insert(word);
                return null;
            }catch(Exception ex){
                Log.e(LOG_TAG, "Failed to insert word");

                Intent broadcastIntent = new Intent(ACTION_ERROR);
                if (ex instanceof SQLiteConstraintException) {
                    broadcastIntent.putExtra(EXTRA_MESSAGE, "Word already in database");
                }
                else{
                    broadcastIntent.putExtra(EXTRA_MESSAGE, "Failed to insert new word.");
                }
                mBroadcastManager.sendBroadcast(broadcastIntent);
                return null;
            }
        }
    }

    private class DeleteAllWordsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void[] voids) {
            mDatabase.getWordDao().deleteAll();
            return null;
        }
    }

    private class DeleteWordTask extends AsyncTask<Word, Void, Void> {

        @Override
        protected Void doInBackground(final Word[] params) {
            mDatabase.getWordDao().deleteWord(params[0]);
            return null;
        }
    }

    private class UpdateWordTask extends AsyncTask<Word, Void, Void>{
        @Override
        protected Void doInBackground(Word[] params) {

            mDatabase.getWordDao().update(params[0]);
            return null;
        }
    }
}
