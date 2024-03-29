package com.example.WordCFExam.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.WordCFExam.dao.base.CrudDao;
import com.example.WordCFExam.entity.Word;

import java.util.List;

@Dao
public abstract class WordDao implements CrudDao<Word> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(Word entity);

    @Update
    public abstract Integer update(Word entity);

    @Delete
    public abstract Integer delete(Word entity);

    @Query("SELECT * FROM word p where p.wordID=:ID")
    abstract public Word findByID(Long ID);


    @Query("SELECT * FROM word p where p.wordString=:wordString and p.profileID=:profileID and p.languageID=:languageID")
    abstract public Word findByWordStringAndProfileIDAndLanguageID(String wordString,Long profileID,Long languageID);


    @Query("SELECT * FROM word p where p.wordString like '%'||:wordStringContain||'%'  and p.profileID=:profileID and p.languageID=:languageID")
    abstract public List<Word> findByWordStringContainsAndProfileIDAndLanguageID(String wordStringContain,Long profileID,Long languageID);

    @Query("SELECT p.*,cfPP.isLoopRepeat,cfPP.CFExamProfileID,cfPP.CFExamProfilePointID,cfPP.lastOfPeriodInMinute,cfPP.name " +
            ",cf.CFExamQuestionnaireID,cf.targetTranslationLanguageID,cf.postponeInMinute,cf.entryPointDateTime,cf.currentCFExamProfilePointID FROM word p " +
            "LEFT JOIN cfexamwordquestionnaire cf on p.wordID=cf.wordID and cf.targetTranslationLanguageID=:targetTranslateLangID " +
            "LEFT JOIN CFExamProfilePoint cfPP on cf.currentCFExamProfilePointID=cfPP.CFExamProfilePointID " +
            " where p.wordString like '%'||:wordStringContain||'%'  and p.profileID=:profileID and p.languageID=:languageID")
    abstract public Cursor findByWordStringContainsAndProfileIDAndLanguageIDCFExamCross(String wordStringContain, Long profileID, Long languageID,Long targetTranslateLangID);





}
