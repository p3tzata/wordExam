package com.example.myapplication.service;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.myapplication.entity.WordOld;
import com.example.myapplication.repository.WordOldRepository;

import org.modelmapper.ModelMapper;

import java.util.List;

public class WordOldService extends AndroidViewModel {

    private WordOldRepository mRepository;

    public WordOldService(Application application) {
        super(application);
        mRepository = new WordOldRepository(application);
        /*WordDao wordDao = DatabaseClient
                .getInstance(application.getApplicationContext())
                .getAppDatabase()
                .wordDao();
        */
    }

    public List<WordOld> getAllWords() {
      return mRepository.getAllWords();
    }

    public void insert(WordOld wordOld) {
        ModelMapper modelMapper = new ModelMapper();
        WordOld map = modelMapper.map(wordOld, WordOld.class);
        mRepository.insert(map);

    }

    public int deleteAll(){
        return mRepository.deleteAll();
    }

    public WordOld findById(Long id) {return mRepository.findById(id); }

    public WordOld findByWordString(String wordString) {return mRepository.findByWordString(wordString); }

    public void update(WordOld wordOld) {mRepository.update(wordOld);}

    public List<WordOld> findAllWordStringContain(String wordStringContain) {
        return mRepository.findAllWordStringContain(wordStringContain);
    }



}
