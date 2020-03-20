package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.Profile;
import com.example.myapplication.entity.Word;

import java.util.List;

@Dao
public abstract class WordDao implements NameableCrudDao<Word> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(Word entity);

    @Update
    public abstract Integer update(Word entity);

    @Delete
    public abstract void delete(Word entity);

    @Query("SELECT * FROM word p where p.wordID=:ID")
    abstract public Word findByID(Long ID);

    @Query("SELECT * FROM word p order by p.wordString")
    abstract public List<Word> findAllOrderAlphabetic();

    @Query("SELECT * FROM word p where p.wordString=:wordString and p.profileID=:profileID and p.languageID=:languageID")
    abstract public Word findByWordStringAndProfileIDAndLanguageID(String wordString,Long profileID,Long languageID);


    @Query("SELECT * FROM word p where p.wordString like '%'||:wordStringContain||'%'  and p.profileID=:profileID and p.languageID=:languageID")
    abstract public List<Word> findByWordStringContainsAndProfileIDAndLanguageID(String wordStringContain,Long profileID,Long languageID);


}
