package com.example.WordCFExam.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.WordCFExam.dao.base.CrudDao;
import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaire;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaireCross;

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
    @Query("SELECT pf.* FROM CFExamWordQuestionnaire q INNER JOIN CFExamProfilePoint p " +
            "on q.currentCFExamProfilePointID=p.CFExamProfilePointID " +
            "INNER JOIN word w on w.wordID=q.wordID " +
            "INNER JOIN profile pf on pf.profileID=w.profileID " +
            "where " +
            "q.entryPointDateTime + " +
            "(IFNULL((q.postponeInMinute*60*1000),0)) " +
            "+ (p.lastOfPeriodInMinute*60*1000) < :currentTime " +
            "AND EXISTS (select 1 from CFExamSchedule s where s.profileID=pf.profileID " +
            " and ( " +
            "(s.fromHour<=s.toHour and s.fromHour<=:currentHour and s.toHour>=:currentHour ) " +
            "or " +
            "(s.fromHour>s.toHour and (s.fromHour<=:currentHour or s.toHour>=:currentHour)) " +
            ")) " +
            "group by pf.profileID"
    )
    abstract public List<Profile> findAllProfileNeedProceed(Long currentTime,int currentHour);

    @Transaction
    @Query("SELECT q.* FROM CFExamWordQuestionnaire q INNER JOIN CFExamProfilePoint p " +
            "on q.currentCFExamProfilePointID=p.CFExamProfilePointID " +
            "INNER JOIN word w on w.wordID=q.wordID and w.profileID=:profileID " +
            "where " +
            "q.entryPointDateTime + " +
            "(IFNULL((q.postponeInMinute*60*1000),0)) " +
            "+ (p.lastOfPeriodInMinute*60*1000) < :currentTime " +
            "ORDER BY RANDOM()"
    )
    abstract public List<CFExamWordQuestionnaireCross> findAllNeedProceed(Long profileID,Long currentTime);




    @Transaction
    @Query("SELECT q.* FROM CFExamWordQuestionnaire q WHere q.wordID=:wordID and q.targetTranslationLanguageID=:toLanguageID" )
    abstract public CFExamWordQuestionnaireCross findByWordID(Long wordID, Long toLanguageID);

    @Query("SELECT hs.* FROM word l " +
            "INNER JOIN translationwordrelation rwr on rwr.nativeWordID=l.wordID " +
            "INNER JOIN word wf on rwr.foreignWordID=wf.wordID " +
            "INNER JOIN helpSentence hs on hs.wordID=wf.wordID and hs.toLanguageID=:fromLanguageID" +
            " where l.wordID=:nativeWordID and wf.languageID=:toLanguageID")
    abstract public List<HelpSentence> findHelpSentenceByNativeQuestionWord(Long nativeWordID, Long toLanguageID, Long fromLanguageID);


}
