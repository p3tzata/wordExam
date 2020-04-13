package com.example.WordCFExam.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.WordCFExam.dao.base.NameableCrudDao;
import com.example.WordCFExam.entity.exam.TopicType;

import java.util.List;

@Dao
public abstract class TopicTypeDao implements NameableCrudDao<TopicType> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(TopicType entity);

    @Update
    public abstract Integer update(TopicType entity);

    @Delete
    public abstract Integer delete(TopicType entity);

    @Query("SELECT * FROM TopicType p where p.topicTypeID=:ID")
    abstract public TopicType findByID(Long ID);

    @Override
    @Query("SELECT * FROM TopicType l where l.profileID=:parentID and l.topicTypeName like '%'||:contains||'%' order by l.topicTypeName")
    abstract public List<TopicType> findAllOrderAlphabetic(Long parentID, String contains);




}
