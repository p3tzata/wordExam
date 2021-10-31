package com.example.WordCFExam.repository;

import android.app.Application;
import android.database.Cursor;

import com.example.WordCFExam.dao.TranslationWordRelationDao;
import com.example.WordCFExam.entity.TranslationWordRelation;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.ForeignWithNativeWords;
import com.example.WordCFExam.entity.dto.WordCFExamCross;
import com.example.WordCFExam.entity.exam.CFExamProfilePoint;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaire;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;


public class TranslationWordRelationRepository extends BaseCrudRepository<TranslationWordRelationDao, TranslationWordRelation> {

    public TranslationWordRelationRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().translationWordRelationDao());
    }


    public ForeignWithNativeWords translateFromForeign(Long foreignWordID){
        return super.getDao().translateFromForeign(foreignWordID);
    }

    /*
    public NativeWithForeignWords translateFromNative(Long nativeWordID){
        return super.getDao().translateFromNative(nativeWordID);
    }

     */


    public List<Word> translateFromNative(Long nativeWordID,Long toLanguageID){
        return super.getDao().translateFromNative(nativeWordID,toLanguageID);
    }

    public List<Word> translateFromForeign(Long foreignWordID,Long toLanguageID){
        return super.getDao().translateFromForeign(foreignWordID, toLanguageID);
    }



    public TranslationWordRelation findByForeignWordIDAndNativeWordID(Long foreignWordID, Long nativeWordID){
        return super.getDao().findByForeignWordIDAndNativeWordID(foreignWordID,nativeWordID);
    }

    public List<TranslationWordRelation> findByNativeWordID(Long nativeWordID){
        return super.getDao().findByNativeWordID(nativeWordID);
    }


    public List<WordCFExamCross> translateFromNativeCFExamCross(Long nativeWordID,Long toLanguageID){

        Cursor cursor = super.getDao().translateFromNativeCFExamCross(nativeWordID, toLanguageID);

        List<WordCFExamCross> listWord = new ArrayList<>();
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




                listWord.add(new WordCFExamCross(word, cfExamProfilePoint ));




            } while (cursor.moveToNext()); //move to next row in the query result

        }


         return listWord;

    }

    public List<WordCFExamCross> translateFromForeignCFExamCross(Long foreignWordID,Long toLanguageID){
        Cursor cursor = super.getDao().translateFromForeignCFExamCross(foreignWordID, toLanguageID);

        List<WordCFExamCross> listWord = new ArrayList<>();
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
               // String debugStop3=null;
                listWord.add(new WordCFExamCross(word, cfExamProfilePoint,cfExamWordQuestionnaire  ));




            } while (cursor.moveToNext()); //move to next row in the query result

        }


        return listWord;
    }


}
