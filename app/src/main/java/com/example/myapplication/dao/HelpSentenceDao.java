package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Profile;

import java.util.List;

@Dao
public abstract class HelpSentenceDao implements CrudDao<HelpSentence> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(HelpSentence entity);

    @Update
    public abstract Integer update(HelpSentence entity);

    @Delete
    public abstract Integer delete(HelpSentence entity);

    @Query("SELECT * FROM helpSentence p where p.helpSentenceID=:ID")
    abstract public HelpSentence findByID(Long ID);

    @Query("SELECT * FROM helpSentence p where p.wordID=:ID")
    abstract public List<HelpSentence> findAllByWordID(Long ID);


    @Query("SELECT * FROM helpSentence l order by l.sentenceString")
    abstract public List<HelpSentence> findAllOrderAlphabetic();

}
