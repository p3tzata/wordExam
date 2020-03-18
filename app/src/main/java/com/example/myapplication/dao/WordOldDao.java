package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.WordOld;

import java.util.List;

@Dao
public interface WordOldDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WordOld wordOld);

    @Query("DELETE FROM word_table")
    int deleteAll();

    @Query("SELECT * from word_table ORDER BY word ASC")
    List<WordOld> getAlphabetizedWords();

    @Update
    void update(WordOld wordOld);

    @Query("SELECT * FROM word_table w WHERE w.id=:wordId ")
    WordOld findById(Long wordId);

    @Query("SELECT * FROM word_table w WHERE w.word=:wordString ")
    WordOld findByWordString(String wordString);

    @Query("SELECT * from word_table w WHERE w.word like '%'||:wordStringContain||'%' ORDER BY word ASC")
    List<WordOld> findAllWordStringContain(String wordStringContain);



}
