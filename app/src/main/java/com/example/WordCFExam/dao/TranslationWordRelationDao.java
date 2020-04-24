package com.example.WordCFExam.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.WordCFExam.dao.base.CrudDao;
import com.example.WordCFExam.entity.TranslationWordRelation;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.ForeignWithNativeWords;


import java.util.List;

@Dao
public abstract class TranslationWordRelationDao implements CrudDao<TranslationWordRelation> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract  public  Long insert(TranslationWordRelation entity);

    @Update
    abstract public  Integer update(TranslationWordRelation entity);

    @Delete
    abstract public  Integer delete(TranslationWordRelation entity);

    @Query("SELECT * FROM translationwordrelation l where l.translationWordRelationID=:ID")
    abstract public TranslationWordRelation findByID(Long ID);

    @Transaction
    @Query("SELECT * FROM word l where l.wordID=:foreignWordID")
    abstract public ForeignWithNativeWords translateFromForeign(Long foreignWordID);

    /*
    @Transaction
    @Query("SELECT l.* FROM word l where l.wordID=:nativeWordID")
    abstract public NativeWithForeignWords translateFromNative(Long nativeWordID);
    */


    @Transaction
    @Query("SELECT wf.* FROM word l " +
            "INNER JOIN translationwordrelation rwr on rwr.nativeWordID=l.wordID " +
            "INNER JOIN word wf on rwr.foreignWordID=wf.wordID" +
            " where l.wordID=:nativeWordID and wf.languageID=:toLanguageID")
    abstract public List<Word> translateFromNative(Long nativeWordID,Long toLanguageID);

    @Transaction
    @Query("SELECT wf.* FROM word l " +
            "INNER JOIN translationwordrelation rwr on rwr.foreignWordID=l.wordID " +
            "INNER JOIN word wf on rwr.nativeWordID=wf.wordID" +
            " where l.wordID=:foreignWordID and wf.languageID=:toLanguageID")
    abstract public List<Word> translateFromForeign(Long foreignWordID,Long toLanguageID);




    @Query("SELECT * FROM translationwordrelation l where l.foreignWordID=:foreignWordID" +
            " and l.nativeWordID=:nativeWordID")
    abstract public TranslationWordRelation findByForeignWordIDAndNativeWordID(Long foreignWordID, Long nativeWordID);

    @Query("SELECT * FROM translationwordrelation l where l.nativeWordID=:nativeWordID")
    abstract public List<TranslationWordRelation> findByNativeWordID(Long nativeWordID);


    @Transaction
    @Query("SELECT wf.*,cfPP.isLoopRepeat,cfPP.CFExamProfileID,cfPP.CFExamProfilePointID,cfPP.lastOfPeriodInMinute,cfPP.name FROM word l " +
            "INNER JOIN translationwordrelation rwr on rwr.nativeWordID=l.wordID " +
            "INNER JOIN word wf on rwr.foreignWordID=wf.wordID " +
            "LEFT JOIN cfexamwordquestionnaire cf on wf.wordID=cf.wordID and cf.targetTranslationLanguageID=l.languageID " +
            "LEFT JOIN CFExamProfilePoint cfPP on cf.currentCFExamProfilePointID=cfPP.CFExamProfilePointID " +
            " where l.wordID=:nativeWordID and wf.languageID=:toLanguageID")
    abstract public Cursor translateFromNativeCFExamCross(Long nativeWordID,Long toLanguageID);

    @Transaction
    @Query("SELECT wf.*,cfPP.isLoopRepeat,cfPP.CFExamProfileID,cfPP.CFExamProfilePointID,cfPP.lastOfPeriodInMinute,cfPP.name FROM word l " +
            "INNER JOIN translationwordrelation rwr on rwr.foreignWordID=l.wordID " +
            "INNER JOIN word wf on rwr.nativeWordID=wf.wordID " +
            "LEFT JOIN cfexamwordquestionnaire cf on wf.wordID=cf.wordID and cf.targetTranslationLanguageID=l.languageID " +
            "LEFT JOIN CFExamProfilePoint cfPP on cf.currentCFExamProfilePointID=cfPP.CFExamProfilePointID " +
            " where l.wordID=:foreignWordID and wf.languageID=:toLanguageID")
    abstract public Cursor translateFromForeignCFExamCross(Long foreignWordID, Long toLanguageID);




}
