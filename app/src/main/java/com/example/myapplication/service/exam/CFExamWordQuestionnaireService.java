package com.example.myapplication.service.exam;

import android.app.Application;

import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.exam.CFExamProfilePoint;
import com.example.myapplication.entity.exam.CFExamWordQuestionnaire;
import com.example.myapplication.entity.exam.CFExamWordQuestionnaireCross;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.repository.exam.CFExamWordQuestionnaireRepository;
import com.example.myapplication.service.TranslationWordRelationService;
import com.example.myapplication.service.base.BaseCrudService;
import com.example.myapplication.service.base.CrudService;

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

    public List<CFExamWordQuestionnaireCross> findAllNeedProceed(){

        return getRepository().findAllNeedProceed(System.currentTimeMillis());
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

}
