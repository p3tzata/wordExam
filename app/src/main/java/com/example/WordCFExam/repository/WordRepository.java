package com.example.WordCFExam.repository;

import android.app.Application;
import android.database.Cursor;

import com.example.WordCFExam.dao.WordDao;
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.WordCFExamCross;
import com.example.WordCFExam.entity.exam.CFExamProfilePoint;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaire;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WordRepository extends BaseCrudRepository<WordDao, Word> {

    public WordRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().wordDao());
    }

    public Word findByWordStringAndProfileIDAndLanguageID(String wordString,Long profileID,Long languageID){

        return super.getDao().findByWordStringAndProfileIDAndLanguageID(wordString,profileID,languageID);

    }

    public List<Word> findByWordStringContainsAndProfileIDAndLanguageID(String wordStringContain, Long profileID, Long languageID){

        return super.getDao().findByWordStringContainsAndProfileIDAndLanguageID(wordStringContain,profileID,languageID);

    }

    public List<WordCFExamCross> findByWordStringContainsAndProfileIDAndLanguageIDCFExamCross(String wordStringContain, Long profileId, Long langID, Long targetTranslateLangID){
        List<WordCFExamCross> listWord = new ArrayList<>();

        Cursor cursor = super.getDao().findByWordStringContainsAndProfileIDAndLanguageIDCFExamCross(wordStringContain, profileId, langID,targetTranslateLangID);

        if (cursor != null && cursor.moveToFirst()){ //make sure you got results, and move to first row
            do{
                Long wordID  = (Long) cursor.getLong(cursor.getColumnIndexOrThrow("wordID"));
                Long profileID  = (Long) cursor.getLong(cursor.getColumnIndexOrThrow("profileID"));
                Long languageID =  (Long) cursor.getLong(cursor.getColumnIndexOrThrow("languageID"));
                Long wordFormID = (Long) cursor.getLong(cursor.getColumnIndexOrThrow("wordFormID"));
                String wordString =  (String) cursor.getString(cursor.getColumnIndexOrThrow("wordString"));

                Integer isLoopRepeat= cursor.getInt(cursor.getColumnIndexOrThrow("isLoopRepeat"));
                Long CFExamProfileID =  cursor.getLong(cursor.getColumnIndexOrThrow("CFExamProfileID"));
                Long CFExamProfilePointID =  cursor.getLong(cursor.getColumnIndexOrThrow("CFExamProfilePointID"));
                Long lastOfPeriodInMinute =  cursor.getLong(cursor.getColumnIndexOrThrow("lastOfPeriodInMinute"));
                String name =  cursor.getString(cursor.getColumnIndexOrThrow("name"));

                Long CFExamQuestionnaireID = cursor.getLong(cursor.getColumnIndexOrThrow("CFExamQuestionnaireID"));
                Long currentCFExamProfilePointID = cursor.getLong(cursor.getColumnIndexOrThrow("currentCFExamProfilePointID"));
                Long entryPointDateTime= cursor.getLong(cursor.getColumnIndexOrThrow("entryPointDateTime"));
                Integer postponeInMinute= cursor.getInt(cursor.getColumnIndexOrThrow("postponeInMinute"));
                Long targetTranslationLanguageID= cursor.getLong(cursor.getColumnIndexOrThrow("targetTranslationLanguageID"));


                Word word = (Word) new Word() {{
                    setWordID(wordID);
                    setProfileID(profileID);
                    setLanguageID(languageID);
                    setWordFormID(wordFormID);
                    setWordString(wordString);
                }};
                CFExamProfilePoint cfExamProfilePoint=null;
                if (CFExamProfilePointID!=null) {
                    cfExamProfilePoint = new CFExamProfilePoint() {{
                        setCFExamProfileID(CFExamProfileID);
                        setCFExamProfilePointID(CFExamProfilePointID);
                        setIsLoopRepeat(isLoopRepeat == 1 ? true : false);
                        setLastOfPeriodInMinute(lastOfPeriodInMinute);
                        setName(name);
                    }};
                }

                CFExamWordQuestionnaire cfExamWordQuestionnaire =new CFExamWordQuestionnaire();
                if (CFExamQuestionnaireID!=null) {
                    cfExamWordQuestionnaire.setCFExamQuestionnaireID(CFExamQuestionnaireID);
                    cfExamWordQuestionnaire.setCurrentCFExamProfilePointID(currentCFExamProfilePointID);
                    cfExamWordQuestionnaire.setEntryPointDateTime(new Date(entryPointDateTime));
                    cfExamWordQuestionnaire.setPostponeInMinute(postponeInMinute);
                    cfExamWordQuestionnaire.setTargetTranslationLanguageID(targetTranslationLanguageID);
                    cfExamWordQuestionnaire.setWordID(wordID);

                }


                listWord.add(new WordCFExamCross(word, cfExamProfilePoint,cfExamWordQuestionnaire ));




            } while (cursor.moveToNext()); //move to next row in the query result

        }

        return listWord;

    }










}
