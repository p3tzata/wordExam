package com.example.myapplication.service;

import android.app.Application;
import android.provider.Telephony;

import com.example.myapplication.entity.PartOfSpeech;
import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.WordPartOfSpeech;
import com.example.myapplication.entity.dto.ForeignWithNativeWords;
import com.example.myapplication.entity.dto.ForeignWordWithDefPartOfSpeech;
import com.example.myapplication.entity.dto.WordCreationDTO;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.repository.TranslationWordRelationRepository;
import com.example.myapplication.repository.WordPartOfSpeechRepository;

import org.modelmapper.ModelMapper;

import java.util.List;


public class WordPartOfSpeechService extends CrudService<WordPartOfSpeechRepository, WordPartOfSpeech> {

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
