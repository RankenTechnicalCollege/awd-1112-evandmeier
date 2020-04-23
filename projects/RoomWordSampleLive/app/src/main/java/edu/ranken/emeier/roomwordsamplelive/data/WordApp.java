package edu.ranken.emeier.roomwordsamplelive.data;

import android.app.Application;

public class WordApp extends Application {

    private WordDatabase mDatabase;
    private WordRepository mRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = WordDatabase.buildDatabase(this);
        mRepository = new WordRepository(this, mDatabase);
    }

    public WordRepository getRepository(){
        return mRepository;
    }
}
