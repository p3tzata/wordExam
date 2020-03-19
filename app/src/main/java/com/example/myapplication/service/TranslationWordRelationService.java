package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.WordCreationDTO;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.repository.TranslationWordRelationRepository;

import org.modelmapper.ModelMapper;


public class TranslationWordRelationService extends CrudService<TranslationWordRelationRepository, TranslationWordRelation> {

    private WordService wordService;

    public TranslationWordRelationService(Application application) {
        super(application,new TranslationWordRelationRepository(application));
        this.wordService=FactoryUtil.createWordService(application);
    }

    public boolean createWordRelation(Word foreignWord, WordCreationDTO wordCreationDTO) {

        Word nativeWord = findOrCreateWord(wordCreationDTO);

        TranslationWordRelation translationWordRelation = new TranslationWordRelation();
        translationWordRelation.setForeignWordID(foreignWord.getWordID());
        translationWordRelation.setNativeWordID(nativeWord.getWordID());
        if (super.getRepository().inset(translationWordRelation)!=null) {
            return true;
        } else {
            return false;
        }




    }

    private Word findOrCreateWord(WordCreationDTO wordCreationDTO) {

        Word word = this.wordService.
                findByWordStringAndProfileIDAndLanguageID(wordCreationDTO.getWordString(),
                        wordCreationDTO.getProfileID(), wordCreationDTO.getLanguageID());

        if (word==null) {
            ModelMapper modelMapper = FactoryUtil.createModelMapper();
            Word creationWord = modelMapper.map(wordCreationDTO, Word.class);
            Long wordID=this.wordService.insert(creationWord);
            word = this.wordService.findByID(wordID);
        }

        return word;




    }


}
