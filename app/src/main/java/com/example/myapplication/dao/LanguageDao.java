package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.Language;

import java.util.List;

@Dao
public abstract class LanguageDao implements NameableCrudDao<Language> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract  public  Long insert(Language entity);

    @Update
    abstract public  void update(Language entity);

    @Delete
    abstract public  void delete(Language entity);

    @Override
    @Query("SELECT * FROM language l order by l.languageName")
    abstract public List<Language> findAllOrderAlphabetic();


    @Query("SELECT * FROM language l where l.languageID=:ID")
    abstract public Language findByID(Long ID);


}
