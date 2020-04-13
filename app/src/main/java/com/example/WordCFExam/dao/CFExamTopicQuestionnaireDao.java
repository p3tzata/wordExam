package com.example.WordCFExam.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.WordCFExam.dao.base.CrudDao;
import com.example.WordCFExam.entity.exam.CFExamTopicQuestionnaire;
import com.example.WordCFExam.entity.exam.CFExamTopicQuestionnaireCross;

import java.util.List;

@Dao
public abstract class CFExamTopicQuestionnaireDao implements CrudDao<CFExamTopicQuestionnaire> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(CFExamTopicQuestionnaire entity);

    @Update
    public abstract Integer update(CFExamTopicQuestionnaire entity);

    @Delete
    public abstract Integer delete(CFExamTopicQuestionnaire entity);

    @Query("SELECT * FROM CFExamTopicQuestionnaire p where p.CFExamQuestionnaireID=:ID")
    abstract public CFExamTopicQuestionnaire findByID(Long ID);


    @Transaction
    @Query("SELECT q.* FROM CFExamTopicQuestionnaire q INNER JOIN CFExamProfilePoint p " +
            "on q.currentCFExamProfilePointID=p.CFExamProfilePointID " +
            "where " +
            "q.entryPointDateTime + " +
            "(IFNULL((q.postponeInMinute*60*1000),0)) " +
            "+ (p.lastOfPeriodInMinute*60*1000) < :currentTime"
    )
    abstract public List<CFExamTopicQuestionnaireCross> findAllNeedProceed(Long currentTime);

    @Transaction
    @Query("SELECT q.* FROM CFExamTopicQuestionnaire q WHere q.topicID=:wordID")
    abstract public CFExamTopicQuestionnaire findByTopicID(Long wordID);


}
