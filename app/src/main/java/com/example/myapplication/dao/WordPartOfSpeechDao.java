package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.WordPartOfSpeech;
import com.example.myapplication.entity.dto.ForeignWithNativeWords;
import com.example.myapplication.entity.dto.ForeignWordWithDefPartOfSpeech;
import com.example.myapplication.entity.dto.TranslationAndLanguages;

import java.util.List;

@Dao
public abstract class WordPartOfSpeechDao implements CrudDao<WordPartOfSpeech> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract  public  Long insert(WordPartOfSpeech entity);

    @Update
    abstract public  Integer update(WordPartOfSpeech entity);

    @Delete
    abstract public  Integer delete(WordPartOfSpeech entity);


    @Query("SELECT * FROM wordpartofspeech t where t.wordPartOfSpeechID=:ID")
    abstract public WordPartOfSpeech findByID(Long ID);

    @Transaction
    @Query("SELECT * FROM word l where l.wordID=:foreignWordID")
    abstract public ForeignWordWithDefPartOfSpeech findForeignWordWithDefPartOfSpeech(Long foreignWordID);

    @Query("SELECT * FROM wordpartofspeech t where t.wordID=:wordID and t.partOfSpeechID=:partOfSpeechID")
    abstract public WordPartOfSpeech findByWordIDAndPartOfSpeechID(Long wordID,Long partOfSpeechID);





}
