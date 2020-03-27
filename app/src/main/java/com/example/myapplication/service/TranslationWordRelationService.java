package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.ForeignWithNativeWords;
import com.example.myapplication.entity.dto.WordCreationDTO;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.repository.TranslationWordRelationRepository;

import org.modelmapper.ModelMapper;

import java.util.List;


public class TranslationWordRelationService extends BaseCrudService<TranslationWordRelationRepository, TranslationWordRelation> {

    private WordService wordService;

    public TranslationWordRelationService(Application application) {
        super(application,new TranslationWordRelationRepository(application));
        this.wordService=FactoryUtil.createWordService(application);
    }

    public ForeignWithNativeWords translateFromForeign(Long foreignWordID){
        return super.getRepository().translateFromForeign(foreignWordID);
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



    public void deleteNativeTranslation(Word foreignWord,Word nativeWord) {

        TranslationWordRelation byForeignWordIDAndNativeWordID = this.findByForeignWordIDAndNativeWordID(foreignWord.getWordID(), nativeWord.getWordID());
        super.getRepository().delete(byForeignWordIDAndNativeWordID);
        List<TranslationWordRelation> byNativeWordID = this.findByNativeWordID(nativeWord.getWordID());
        if (byNativeWordID.size()==0) {
            wordService.delete(nativeWord);
        }

    }


    public TranslationWordRelation findByForeignWordIDAndNativeWordID(Long foreignWordID, Long nativeWordID){
        return super.getRepository().findByForeignWordIDAndNativeWordID(foreignWordID,nativeWordID);
    }

    public List<TranslationWordRelation> findByNativeWordID(Long nativeWordID){
        return super.getRepository().findByNativeWordID(nativeWordID);
    }




}
