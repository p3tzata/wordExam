package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplication.dao.base.CrudDao;
import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.ForeignWithNativeWords;
import com.example.myapplication.entity.dto.NativeWithForeignWords;


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


    @Query("SELECT * FROM translationwordrelation l where l.foreignWordID=:foreignWordID" +
            " and l.nativeWordID=:nativeWordID")
    abstract public TranslationWordRelation findByForeignWordIDAndNativeWordID(Long foreignWordID, Long nativeWordID);

    @Query("SELECT * FROM translationwordrelation l where l.nativeWordID=:nativeWordID")
    abstract public List<TranslationWordRelation> findByNativeWordID(Long nativeWordID);






}
