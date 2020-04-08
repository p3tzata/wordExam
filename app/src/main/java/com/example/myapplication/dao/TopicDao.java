package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.dao.base.NameableCrudDao;
import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.exam.Topic;

import java.util.List;

@Dao
public abstract class TopicDao implements NameableCrudDao<Topic> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(Topic entity);

    @Update
    public abstract Integer update(Topic entity);

    @Delete
    public abstract Integer delete(Topic entity);

    @Query("SELECT * FROM Topic p where p.topicID=:ID")
    abstract public Topic findByID(Long ID);

    @Override
    @Query("SELECT * FROM Topic l where l.topicTypeID=:parentID and l.topicQuestion like '%'||:contains||'%' order by l.topicQuestion")
    abstract public List<Topic> findAllOrderAlphabetic(Long parentID, String contains);




}
