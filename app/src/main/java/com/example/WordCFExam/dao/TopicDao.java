package com.example.WordCFExam.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.WordCFExam.dao.base.NameableCrudDao;
import com.example.WordCFExam.entity.Topic;

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

    @Query("SELECT l.topicTypeID,l.*,cfPP.isLoopRepeat,cfPP.CFExamProfileID,cfPP.CFExamProfilePointID,cfPP.lastOfPeriodInMinute,cfPP.name FROM Topic l " +
            "LEFT JOIN cfexamtopicquestionnaire cf on l.topicID=cf.topicID " +
            "LEFT JOIN CFExamProfilePoint cfPP on cf.currentCFExamProfilePointID=cfPP.CFExamProfilePointID " +
            " where l.topicTypeID=:parentID and l.topicQuestion like '%'||:topicStringContain||'%'")
    abstract public Cursor findByTopicStringContainsAndParentIDCFExamCross(Long parentID, String topicStringContain);



}
