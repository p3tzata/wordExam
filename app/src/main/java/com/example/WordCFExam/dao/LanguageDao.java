package com.example.WordCFExam.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.WordCFExam.dao.base.NameableCrudDao;
import com.example.WordCFExam.entity.Language;

import java.util.List;

@Dao
public abstract class LanguageDao implements NameableCrudDao<Language> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract  public  Long insert(Language entity);

    @Update
    abstract public  Integer update(Language entity);

    @Delete
    abstract public  Integer delete(Language entity);


    @Override
    @Query("SELECT * FROM language l where :parentID=:parentID and l.languageName like '%'||:contains||'%' order by l.languageName")
    abstract public List<Language> findAllOrderAlphabetic(Long parentID,String contains);


    @Query("SELECT * FROM language l where l.languageID=:ID")
    abstract public Language findByID(Long ID);


}
