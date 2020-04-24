package com.example.WordCFExam.service;

import android.app.Application;

import com.example.WordCFExam.entity.TranslationWordRelation;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.ForeignWithNativeWords;
import com.example.WordCFExam.entity.dto.WordCFExamCross;
import com.example.WordCFExam.entity.dto.WordCreationDTO;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.repository.TranslationWordRelationRepository;
import com.example.WordCFExam.service.base.BaseCrudService;

import org.modelmapper.ModelMapper;

import java.util.List;


public class TranslationWordRelationService extends BaseCrudService<TranslationWordRelationRepository, TranslationWordRelation> {

    private WordService wordService;

    public TranslationWordRelationService(Application application) {
        super(application,new TranslationWordRelationRepository(application));
        this.wordService=FactoryUtil.createWordService(application);
    }
/*
    public NativeWithForeignWords translateFromNative(Long nativeWordID){
        return super.getRepository().translateFromNative(nativeWordID);
    }
*/
    public List<Word> translateFromNative(Long nativeWordID, Long toLanguageID){
        return super.getRepository().translateFromNative(nativeWordID,toLanguageID);
    }

    public List<Word> translateFromForeign(Long foreignWordID,Long toLanguageID){
        return super.getRepository().translateFromForeign(foreignWordID, toLanguageID);
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


    public List<WordCFExamCross> translateFromNativeCFExamCross(Long nativeWordID, Long toLanguageID){
        return super.getRepository().translateFromNativeCFExamCross(nativeWordID,toLanguageID);
    }

    public List<WordCFExamCross> translateFromForeignCFExamCross(Long foreignWordID,Long toLanguageID){
        return super.getRepository().translateFromForeignCFExamCross(foreignWordID, toLanguageID);
    }




}
