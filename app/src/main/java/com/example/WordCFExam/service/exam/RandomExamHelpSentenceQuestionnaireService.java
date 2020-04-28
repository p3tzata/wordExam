package com.example.WordCFExam.service.exam;

import android.app.Application;

import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.RandomExamCounter;
import com.example.WordCFExam.entity.exam.RandomExamHelpSentencePassedQuestionnaire;
import com.example.WordCFExam.entity.exam.RandomExamWordPassedQuestionnaire;
import com.example.WordCFExam.repository.LanguageRepository;
import com.example.WordCFExam.repository.exam.RandomExamHelpSentencePassedQuestionnaireRepository;
import com.example.WordCFExam.repository.exam.RandomExamWordPassedQuestionnaireRepository;
import com.example.WordCFExam.service.base.BaseCrudService;
import com.example.WordCFExam.service.base.BaseNameCrudService;

import java.util.ArrayList;
import java.util.List;


public class RandomExamHelpSentenceQuestionnaireService extends BaseCrudService<RandomExamHelpSentencePassedQuestionnaireRepository, RandomExamWordPassedQuestionnaire> {

    private CFExamProfilePointService cfExamProfilePointService;


    public RandomExamHelpSentenceQuestionnaireService(Application application) {
        super(application,new RandomExamHelpSentencePassedQuestionnaireRepository(application));

    }



    public boolean examProcessedOK(RandomExamHelpSentencePassedQuestionnaire item) {
        getRepository().inset(item);
        return true;
    }


    public boolean examProcessedFail(RandomExamHelpSentencePassedQuestionnaire item) {

        return true;


    }



    public List<HelpSentence>  findAllNativeRandom(Long profileID, Long fromLanguageID, Long toLanguageID,Integer countNumber){

        List<HelpSentence> allNativeRandom = super.getRepository().findAllNativeRandom(profileID, fromLanguageID, toLanguageID, countNumber);
        if (allNativeRandom.size()==0) {
            getRepository().deleteNativeAll(profileID, fromLanguageID, toLanguageID);
            return super.getRepository().findAllNativeRandom(profileID, fromLanguageID, toLanguageID, countNumber);
        }
        return allNativeRandom;

    }

    public List<HelpSentence>  findAllForeignRandom(Long profileID, Long fromLanguageID, Long toLanguageID,Integer countNumber){

        List<HelpSentence> allForeignRandom = super.getRepository().findAllForeignRandom(profileID, fromLanguageID, toLanguageID, countNumber);
        if (allForeignRandom.size()==0) {
            getRepository().deleteForeignAll(profileID, fromLanguageID, toLanguageID);
            return super.getRepository().findAllForeignRandom(profileID, fromLanguageID, toLanguageID, countNumber);
        }
        return allForeignRandom;
    }


    public List<String> examCheckAnswer(boolean isTranslateToForeign, HelpSentence helpSentence, String toString) {
        List<String> result =new ArrayList<>();
        if (isTranslateToForeign) {
            if (helpSentence.getSentenceString().toLowerCase().equals(toString.toLowerCase())) {
                result.add("Translated OK");
            } else {
                result.add("Translated Fail");
            }
        } else {
            if (helpSentence.getSentenceTranslation().toLowerCase().equals(toString.toLowerCase())) {
                result.add("Translated OK");
            } else {
                result.add("Translated Fail");
            }

        }
        return result;


    }

    public RandomExamCounter findAllNativeRandomCounter(Long profileID, Long fromLanguageID, Long toLanguageID){
        return getRepository().findAllNativeRandomCounter(profileID,fromLanguageID,toLanguageID);
    }

    public RandomExamCounter findAllForeignRandomCounter(Long profileID, Long fromLanguageID, Long toLanguageID){
        return getRepository().findAllForeignRandomCounter(profileID,fromLanguageID,toLanguageID);
    }

}
