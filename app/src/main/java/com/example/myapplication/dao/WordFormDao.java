package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.dao.base.CrudDao;
import com.example.myapplication.dao.base.NameableCrudDao;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.WordForm;

import java.util.List;

@Dao
public abstract class WordFormDao implements NameableCrudDao<WordForm> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(WordForm entity);

    @Update
    public abstract Integer update(WordForm entity);

    @Delete
    public abstract Integer delete(WordForm entity);

    @Query("SELECT * FROM WordForm p where p.wordFormID=:ID")
    abstract public WordForm findByID(Long ID);

    @Query("SELECT * FROM WordForm p where p.languageID=:ID order by p.wordFormName")
    abstract public List<WordForm> findAllByLanguageID(Long ID);

    @Override
    @Query("SELECT * FROM WordForm l where l.languageID=:parentID and l.wordFormName like '%'||:contains||'%' order by l.wordFormName")
    abstract public List<WordForm> findAllOrderAlphabetic(Long parentID, String contains);



}
