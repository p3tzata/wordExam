package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.dao.base.CrudDao;
import com.example.myapplication.entity.PartOfSpeech;

import java.util.List;

@Dao
public abstract class PartOfSpeechDao implements CrudDao<PartOfSpeech> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(PartOfSpeech entity);

    @Update
    public abstract Integer update(PartOfSpeech entity);

    @Delete
    public abstract Integer delete(PartOfSpeech entity);

    @Query("SELECT * FROM partOfSpeech p where p.languageID=:languageID order by p.name")
    abstract public List<PartOfSpeech> findAllOrderAlphabetic(Long languageID);

    @Query("SELECT * FROM partOfSpeech p where p.partOfSpeechID=:ID")
    abstract public PartOfSpeech findByID(Long ID);

    @Query("SELECT * FROM partOfSpeech p where p.languageID=:languageID order by p.name asc")
    abstract public List<PartOfSpeech> findAllByLanguageID(Long languageID);



}
