package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;


import com.example.myapplication.dao.base.CrudDao;
import com.example.myapplication.dao.base.NameableCrudDao;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.dto.TranslationAndLanguages;

import java.util.List;

@Dao
public abstract class TranslationDao implements NameableCrudDao<Translation> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract  public  Long insert(Translation entity);

    @Update
    abstract public  Integer update(Translation entity);

    @Delete
    abstract public  Integer delete(Translation entity);

    @Query("SELECT * FROM translation t order by t.translationName")
    abstract public List<Translation> findAllOrderAlphabetic();

    @Query("SELECT * FROM translation t where t.translationID=:ID")
    abstract public Translation findByID(Long ID);

    @Transaction
    @Query("SELECT * FROM translation t where t.profileID=:profileID")
    abstract public List<Translation> findAllByProfile(Long profileID);

    @Transaction
    //@Query("SELECT * FROM translation t INNER JOIN Language fLang on fLang.languageID=t.foreignLanguageID " +
    @Query("SELECT * FROM translation t " +
           // "INNER JOIN Language nLang on nLang.languageID = t.nativeLanguageID " +
            "where t.profileID=:profileID")
    abstract public List<TranslationAndLanguages> findAllTransAndLangByProfile(Long profileID);


    @Override
    @Query("SELECT * FROM translation l where l.profileID=:parentID and l.translationName like '%'||:contains||'%' order by l.translationName")
    abstract public List<Translation> findAllOrderAlphabetic(Long parentID, String contains);






}
