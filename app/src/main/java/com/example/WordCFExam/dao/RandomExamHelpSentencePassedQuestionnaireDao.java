package com.example.WordCFExam.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.WordCFExam.dao.base.CrudDao;
import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.RandomExamCounter;
import com.example.WordCFExam.entity.exam.RandomExamHelpSentencePassedQuestionnaire;
import com.example.WordCFExam.entity.exam.RandomExamWordPassedQuestionnaire;

import java.util.List;

@Dao
public abstract class RandomExamHelpSentencePassedQuestionnaireDao implements CrudDao<RandomExamHelpSentencePassedQuestionnaire> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(RandomExamHelpSentencePassedQuestionnaire entity);

    @Update
    public abstract Integer update(RandomExamHelpSentencePassedQuestionnaire entity);

    @Delete
    public abstract Integer delete(RandomExamHelpSentencePassedQuestionnaire entity);

    @Query("delete from RandomExamHelpSentencePassedQuestionnaire " +
            "where EXISTS (select 1 from helpSentence hs INNER JOIN word w ON hs.wordID=w.wordID " +
                "where hs.toLanguageID=:toLanguageID " +
                "and w.languageID=:fromLanguageID and w.profileID=:profileID) "
            )
    abstract public void deleteForeignAll(Long profileID, Long fromLanguageID,Long toLanguageID);

    @Query("delete from RandomExamHelpSentencePassedQuestionnaire " +
            "where EXISTS (select 1 from helpSentence hs INNER JOIN word w ON hs.wordID=w.wordID " +
            "where hs.toLanguageID=:fromLanguageID " +
            "and w.languageID=:toLanguageID and w.profileID=:profileID) "
    )
    abstract public void deleteNativeAll(Long profileID, Long fromLanguageID,Long toLanguageID);



    @Query("SELECT * FROM RandomExamHelpSentencePassedQuestionnaire p where p.RandomExamPassedQuestionnaireID=:ID")
    abstract public RandomExamHelpSentencePassedQuestionnaire findByID(Long ID);


    @Query("SELECT t.* FROM " +
            "(" +
            "SELECT hs.* FROM helpSentence hs INNER JOIN word w on w.wordID=hs.wordID " +
            " where w.languageID=:fromLanguageID and hs.toLanguageID=:toLanguageID " +
            "and w.profileID=:profileID " +
            "and not EXISTS (select 1 from RandomExamHelpSentencePassedQuestionnaire rExam where rExam.helpSentenceID=hs.helpSentenceID and rExam.targetTranslationLanguageID=:toLanguageID) " +
            "ORDER BY RANDOM() LIMIT :countNumber" +
            ") t " +
            "ORDER BY t.sentenceString")
    public abstract List<HelpSentence> findAllForeignRandom(Long profileID, Long fromLanguageID, Long toLanguageID, Integer countNumber);

    @Query("SELECT t.* FROM " +
            "(" +
            "SELECT hs.* FROM helpSentence hs INNER JOIN word w on w.wordID=hs.wordID " +
            " where w.languageID=:toLanguageID and hs.toLanguageID=:fromLanguageID " +
            "and w.profileID=:profileID " +
            "and not EXISTS (select 1 from RandomExamHelpSentencePassedQuestionnaire rExam where rExam.helpSentenceID=hs.helpSentenceID and rExam.targetTranslationLanguageID=:toLanguageID) " +
            "ORDER BY RANDOM() LIMIT :countNumber" +
            ") t " +
            "ORDER BY t.sentenceString")
    public abstract List<HelpSentence> findAllNativeRandom(Long profileID, Long fromLanguageID, Long toLanguageID, Integer countNumber);


    @Query("SELECT COUNT(1) as total, sum(t.point) as  points FROM (SELECT CASE when rExam.helpSentenceID is null then 0 else 1 end as point " +
            "FROM helpSentence hs INNER JOIN word w on w.wordID=hs.wordID " +
            "LEFT JOIN RandomExamHelpSentencePassedQuestionnaire rExam ON rExam.helpSentenceID=hs.helpSentenceID and rExam.targetTranslationLanguageID=:toLanguageID " +
            " where w.languageID=:toLanguageID and hs.toLanguageID=:fromLanguageID " +
            "and w.profileID=:profileID ) t"
            )
    public abstract RandomExamCounter findAllNativeRandomCounter(Long profileID, Long fromLanguageID, Long toLanguageID);

    @Query("SELECT COUNT(1) as total, sum(t.point) as  points FROM (SELECT CASE when rExam.helpSentenceID is null then 0 else 1 end as point " +
            "FROM helpSentence hs INNER JOIN word w on w.wordID=hs.wordID " +
            "LEFT JOIN RandomExamHelpSentencePassedQuestionnaire rExam ON rExam.helpSentenceID=hs.helpSentenceID and rExam.targetTranslationLanguageID=:toLanguageID " +
            " where w.languageID=:fromLanguageID and hs.toLanguageID=:toLanguageID " +
            "and w.profileID=:profileID ) t"
    )
    public abstract RandomExamCounter findAllForeignRandomCounter(Long profileID, Long fromLanguageID, Long toLanguageID);





}
