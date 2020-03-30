package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.dao.base.CrudDao;
import com.example.myapplication.dao.base.NameableCrudDao;
import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.entity.Language;

import java.util.List;

@Dao
public abstract class HelpSentenceDao implements NameableCrudDao<HelpSentence> {

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

    @Override
    @Query("SELECT * FROM helpSentence l where l.wordID=:parentID and l.sentenceString like '%'||:contains||'%' order by l.sentenceString")
    abstract public List<HelpSentence> findAllOrderAlphabetic(Long parentID, String contains);


}
