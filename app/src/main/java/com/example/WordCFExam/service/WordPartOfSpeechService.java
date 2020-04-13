package com.example.WordCFExam.service;

import android.app.Application;

import com.example.WordCFExam.entity.PartOfSpeech;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.WordPartOfSpeech;
import com.example.WordCFExam.entity.dto.ForeignWordWithDefPartOfSpeech;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.repository.WordPartOfSpeechRepository;
import com.example.WordCFExam.service.base.BaseCrudService;


public class WordPartOfSpeechService extends BaseCrudService<WordPartOfSpeechRepository, WordPartOfSpeech> {

    private WordService wordService;

    public WordPartOfSpeechService(Application application) {
        super(application,new WordPartOfSpeechRepository(application));
        this.wordService=FactoryUtil.createWordService(application);
    }

    public ForeignWordWithDefPartOfSpeech findForeignWordWithDefPartOfSpeech(Long foreignWordID){
        return super.getRepository().findForeignWordWithDefPartOfSpeech(foreignWordID);
    }

    public WordPartOfSpeech findByWordIDAndPartOfSpeech(Long wordID, Long partOfSpeechID){
        return super.getRepository().findByWordIDAndPartOfSpeechID(wordID,partOfSpeechID);
    }

    public void deleteDefPartOfSpeech(Word foreignWord, PartOfSpeech partOfSpeech) {

        WordPartOfSpeech findByWordIDAndPartOfSpeech = this.findByWordIDAndPartOfSpeech(foreignWord.getWordID(), partOfSpeech.getPartOfSpeechID());
        super.getRepository().delete(findByWordIDAndPartOfSpeech);

    }



}
