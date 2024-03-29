package com.example.WordCFExam.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.WordCFExam.dao.base.NameableCrudDao;
import com.example.WordCFExam.entity.exam.CFExamProfilePoint;
import com.example.WordCFExam.entity.exam.CFExamProfilePointCross;

import java.util.List;
@Dao
public abstract class CFExamProfilePointDao implements NameableCrudDao<CFExamProfilePoint> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(CFExamProfilePoint entity);

    @Update
    public abstract Integer update(CFExamProfilePoint entity);

    @Delete
    public abstract Integer delete(CFExamProfilePoint entity);

    @Query("SELECT * FROM CFExamProfilePoint p where p.CFExamProfilePointID=:ID")
    abstract public CFExamProfilePoint findByID(Long ID);


    @Override
    @Query("SELECT * FROM CFExamProfilePoint l where l.CFExamProfileID=:parentID and l.name like '%'||:contains||'%' order by l.lastOfPeriodInMinute asc")
    abstract public List<CFExamProfilePoint> findAllOrderAlphabetic(Long parentID, String contains);

    @Query("SELECT * FROM CFExamProfilePoint pp where pp.lastOfPeriodInMinute>=:lastOfPeriodInMinute " +
            "and pp.CFExamProfileID=:currentProfileID and pp.CFExamProfilePointID!=:currentProfilePointID " +
            "order by pp.lastOfPeriodInMinute")
    abstract public List<CFExamProfilePoint>
    findByGreatLastOfPeriod(Long currentProfileID,Long currentProfilePointID,Long lastOfPeriodInMinute);

    @Query("SELECT * from CFExamProfilePoint pp where pp.CFExamProfileID=:cfExamProfileID order by pp.lastOfPeriodInMinute asc")
    public abstract List<CFExamProfilePoint> findAllByOrderByLastOfPeriod(Long cfExamProfileID);

    @Query("SELECT * FROM CFExamProfilePoint pp where pp.lastOfPeriodInMinute<=:lastOfPeriodInMinute " +
            "and pp.CFExamProfileID=:currentProfileID and pp.CFExamProfilePointID!=:currentProfilePointID " +
            "order by pp.lastOfPeriodInMinute desc")
    abstract public List<CFExamProfilePoint>
    findPreviousLastOfPeriod(Long currentProfileID,Long currentProfilePointID,Long lastOfPeriodInMinute);



    @Transaction
    @Query("SELECT * FROM CFExamProfilePoint p where p.CFExamProfilePointID=:ID")
    abstract public CFExamProfilePointCross findCrossByID(Long ID);

}



