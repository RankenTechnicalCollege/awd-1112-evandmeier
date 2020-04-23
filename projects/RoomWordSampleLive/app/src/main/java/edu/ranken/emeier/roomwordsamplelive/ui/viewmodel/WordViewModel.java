package edu.ranken.emeier.roomwordsamplelive.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.ranken.emeier.roomwordsamplelive.data.WordApp;
import edu.ranken.emeier.roomwordsamplelive.data.WordRepository;
import edu.ranken.emeier.roomwordsamplelive.data.entitiy.Word;

public class WordViewModel extends AndroidViewModel {

    private WordApp mApp;
    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(Application app){
        super(app);
        mApp = (WordApp) app;
        mRepository = mApp.getRepository();
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

    public void insertWord(Word word){
        mRepository.insertWord(word);
    }

    public void updateWord(Word word){
        mRepository.updateWord(word);
    }

    public void deleteAll() {mRepository.deleteAllWords();}

    public void deleteWord(Word word) {mRepository.deleteWord(word);}


}