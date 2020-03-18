package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.WordOldDao;
import com.example.myapplication.database.DatabaseClient;
import com.example.myapplication.database.WordRoomDatabase;
import com.example.myapplication.entity.WordOld;

import java.util.List;

public class WordOldRepository {

    private WordOldDao mWordOldDao;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public WordOldRepository(Application application) {
        WordRoomDatabase appDatabase = DatabaseClient.getInstance(application).getAppDatabase();


        mWordOldDao = appDatabase.wordOldDao();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public List<WordOld> getAllWords() {
        return mWordOldDao.getAlphabetizedWords();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(WordOld wordOld) {
        mWordOldDao.insert(wordOld);
    }

    public int deleteAll() {
      return mWordOldDao.deleteAll();
    }

    public WordOld findById(Long id) {return mWordOldDao.findById(id);}

    public WordOld findByWordString(String wordString) {return mWordOldDao.findByWordString(wordString);}

    public void update(WordOld wordOld) {
        mWordOldDao.update(wordOld);}

    public List<WordOld> findAllWordStringContain(String wordStringContain) {
        return mWordOldDao.findAllWordStringContain(wordStringContain);
    }



}