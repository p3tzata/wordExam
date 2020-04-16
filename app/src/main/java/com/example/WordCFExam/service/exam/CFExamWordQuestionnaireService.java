package com.example.WordCFExam.service.exam;

import android.app.Application;
import android.content.Context;

import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamProfilePoint;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaire;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaireCross;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.repository.exam.CFExamWordQuestionnaireRepository;
import com.example.WordCFExam.service.TranslationWordRelationService;
import com.example.WordCFExam.service.base.BaseCrudService;
import com.example.WordCFExam.service.base.CrudService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;


public class CFExamWordQuestionnaireService extends BaseCrudService<CFExamWordQuestionnaireRepository, CFExamWordQuestionnaire>
implements CrudService<CFExamWordQuestionnaire>,ExamQuestionnaireService<CFExamWordQuestionnaire> {

    private CFExamProfilePointService cfExamProfilePointService;
    private TranslationWordRelationService translationWordRelationService;

    public CFExamWordQuestionnaireService(Application application) {
        super(application,new CFExamWordQuestionnaireRepository(application));
        cfExamProfilePointService= FactoryUtil.createCFExamProfilePointService(getApplication());
        translationWordRelationService = FactoryUtil.createTranslationWordRelationService(getApplication());
    }

    public List<Profile> findAllProfileNeedProceed(){
        DateFormat dateFormat = new SimpleDateFormat("HH");
        String formattedEntryPointDate = dateFormat.format(Calendar.getInstance().getTime());
        int currentHour=Integer.valueOf(formattedEntryPointDate);
        return getRepository().findAllProfileNeedProceed(System.currentTimeMillis(),currentHour);
    }

    public List<CFExamWordQuestionnaireCross> findAllNeedProceed(Long profileID){

        return getRepository().findAllNeedProceed(profileID, System.currentTimeMillis());
    }



    @Override
    public boolean examProcessedOK(CFExamWordQuestionnaire item) {

        CFExamProfilePoint currentPoint = cfExamProfilePointService.findByID(item.getCurrentCFExamProfilePointID());

        if (currentPoint.getIsLoopRepeat()) {
            item.setEntryPointDateTime(Calendar.getInstance().getTime());
            item.setPostponeInMinute(null);
            super.update(item);
        } else {
            CFExamProfilePoint nextProfilePoint = cfExamProfilePointService.findNextProfilePoint(item.getCurrentCFExamProfilePointID());
            if (nextProfilePoint==null) {
                super.delete(item);
            } else {
                item.setCurrentCFExamProfilePointID(nextProfilePoint.getCFExamProfilePointID());
                item.setEntryPointDateTime(Calendar.getInstance().getTime());
                item.setPostponeInMinute(null);
                super.update(item);
            }
        }
        return true;
    }

    @Override
    public boolean examProcessedFail(CFExamWordQuestionnaire item) {
        CFExamProfilePoint currentPoint = cfExamProfilePointService.findByID(item.getCurrentCFExamProfilePointID());
        CFExamProfilePoint firstProfilePoint = cfExamProfilePointService.findFirstProfilePoint(currentPoint);
        if (firstProfilePoint!=null) {
            item.setCurrentCFExamProfilePointID(firstProfilePoint.getCFExamProfilePointID());
            item.setPostponeInMinute(null);
            item.setEntryPointDateTime(Calendar.getInstance().getTime());
            super.update(item);
            return true;
        }
        return false;


    }


    public List<String> examCheckAnswer(Boolean isTranslateToForeign, Word word, Long toLanguageID, String answer){

        int okAnswer=0;
        int failAnswer=0;
        int unAnswer=0;

        List<String> result=new ArrayList<>();
        List<String> rawWordStrings =  new ArrayList<String>(Arrays.asList(answer.split(",")));
        List<String> answerWordStrings = new ArrayList<>();
        for (String rawWord : rawWordStrings) {
            answerWordStrings.add(rawWord.trim().toLowerCase());
        }


        List<Word> translatedWord=null;
        List<String> translatedWordString=new ArrayList<>();

        if (isTranslateToForeign) {

           translatedWord = translationWordRelationService.translateFromNative(word.getWordID(),toLanguageID);
        } else {
            translatedWord = translationWordRelationService.translateFromForeign(word.getWordID(), toLanguageID);
        }
        if (translatedWord!=null) {
            for (Word transWord : translatedWord) {
                translatedWordString.add(transWord.getWordString());
            }


            ListIterator<String> iter = answerWordStrings.listIterator();
            while (iter.hasNext()) {
                String currentAnswer = iter.next();
                if (translatedWordString.contains(currentAnswer.toLowerCase())) {
                    okAnswer++;
                    translatedWordString.remove(currentAnswer);
                } else {
                    failAnswer++;
                }
            }
            unAnswer = translatedWordString.size();
            result.add("Translated OK: " + okAnswer);
            result.add("Translated Fail: " + failAnswer);
            result.add("Translated Missing: " + unAnswer);
        } else {
            result.add("There is no translation");
        }

        return result;
    }

    public CFExamWordQuestionnaireCross findByWordID(Long wordID, Long toLanguageID){
        return getRepository().findByWordID(wordID, toLanguageID);
    }


    public boolean create(Long wordID,Long languageID, CFExamProfile cfExamProfile) {


        CFExamProfilePoint firstProfilePoint = cfExamProfilePointService.findFirstProfilePoint(cfExamProfile.getCFExamProfileID());
        CFExamWordQuestionnaire cfExamWordQuestionnaire = new CFExamWordQuestionnaire();
        cfExamWordQuestionnaire.setCurrentCFExamProfilePointID(firstProfilePoint.getCFExamProfilePointID());
        cfExamWordQuestionnaire.setWordID(wordID);
        cfExamWordQuestionnaire.setTargetTranslationLanguageID(languageID);

        cfExamWordQuestionnaire.setEntryPointDateTime(Calendar.getInstance().getTime());
        Long insert = this.insert(cfExamWordQuestionnaire);
        if (insert>0L) {
            return true;
        } else {
            return false;
        }



    }



}
