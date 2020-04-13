package com.example.WordCFExam.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.WordCFExam.dao.base.NameableCrudDao;
import com.example.WordCFExam.entity.exam.CFExamProfile;

import java.util.List;
@Dao
public abstract class CFExamProfileDao implements NameableCrudDao<CFExamProfile> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(CFExamProfile entity);

    @Update
    public abstract Integer update(CFExamProfile entity);

    @Delete
    public abstract Integer delete(CFExamProfile entity);

    @Query("SELECT * FROM CFExamProfile p where p.CFExamProfileID=:ID")
    abstract public CFExamProfile findByID(Long ID);


    @Override
    @Query("SELECT * FROM CFExamProfile l where l.ProfileID=:parentID and l.name like '%'||:contains||'%' order by l.name")
    abstract public List<CFExamProfile> findAllOrderAlphabetic(Long parentID, String contains);
}
