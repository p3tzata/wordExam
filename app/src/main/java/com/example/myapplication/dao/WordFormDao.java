package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.WordForm;

import java.util.List;

@Dao
public abstract class WordFormDao implements CrudDao<WordForm> {

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



}