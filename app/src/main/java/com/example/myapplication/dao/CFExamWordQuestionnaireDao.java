package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplication.dao.base.CrudDao;
import com.example.myapplication.entity.exam.CFExamWordQuestionnaire;
import com.example.myapplication.entity.exam.CFExamWordQuestionnaireCross;

import java.util.List;

@Dao
public abstract class CFExamWordQuestionnaireDao implements CrudDao<CFExamWordQuestionnaire> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(CFExamWordQuestionnaire entity);

    @Update
    public abstract Integer update(CFExamWordQuestionnaire entity);

    @Delete
    public abstract Integer delete(CFExamWordQuestionnaire entity);

    @Query("SELECT * FROM CFExamWordQuestionnaire p where p.CFExamQuestionnaireID=:ID")
    abstract public CFExamWordQuestionnaire findByID(Long ID);


    @Transaction
    @Query("SELECT q.* FROM CFExamWordQuestionnaire q INNER JOIN CFExamProfilePoint p " +
            "on q.currentCFExamProfilePointID=p.CFExamProfilePointID " +
            "where " +
            "q.entryPointDateTime + " +
            "(IFNULL((q.postponeInMinute*60*1000),0)) " +
            "+ (p.lastOfPeriodInMinute*60*1000) < :currentTime"
    )
    abstract public List<CFExamWordQuestionnaireCross> findAllNeedProceed(Long currentTime);




}
