package com.example.WordCFExam.service.exam;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.repository.BaseCrudRepository;
import com.example.WordCFExam.service.TranslationWordRelationService;
import com.example.WordCFExam.service.base.BaseCrudService;
import com.example.WordCFExam.service.base.CrudService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public abstract class BaseExamQuestionnaireService<R extends BaseCrudRepository,T> extends BaseCrudService<R, T>
        implements CrudService<T>,ExamQuestionnaireService<T> {

    private TranslationWordRelationService translationWordRelationService;

    public BaseExamQuestionnaireService(@NonNull Application application, R repository) {
        super(application, repository);
        translationWordRelationService = FactoryUtil.createTranslationWordRelationService(getApplication());
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
