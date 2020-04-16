package com.example.WordCFExam.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.WordCFExam.dao.base.NameableCrudDao;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamSchedule;

import java.util.List;

@Dao
public abstract class CFExamScheduleDao implements NameableCrudDao<CFExamSchedule> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(CFExamSchedule entity);

    @Update
    public abstract Integer update(CFExamSchedule entity);

    @Delete
    public abstract Integer delete(CFExamSchedule entity);

    @Query("SELECT * FROM CFExamSchedule p where p.CFExamSchedule=:ID")
    abstract public CFExamSchedule findByID(Long ID);


    @Override
    @Query("SELECT * FROM CFExamSchedule l where l.ProfileID=:parentID and :contains=:contains order by l.fromHour")
    abstract public List<CFExamSchedule> findAllOrderAlphabetic(Long parentID, String contains);
}
