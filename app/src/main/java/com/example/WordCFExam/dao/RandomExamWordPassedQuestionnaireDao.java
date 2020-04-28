package com.example.WordCFExam.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.WordCFExam.dao.base.CrudDao;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.RandomExamCounter;
import com.example.WordCFExam.entity.exam.RandomExamWordPassedQuestionnaire;

import java.util.List;

@Dao
public abstract class RandomExamWordPassedQuestionnaireDao implements CrudDao<RandomExamWordPassedQuestionnaire> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(RandomExamWordPassedQuestionnaire entity);

    @Update
    public abstract Integer update(RandomExamWordPassedQuestionnaire entity);

    @Delete
    public abstract Integer delete(RandomExamWordPassedQuestionnaire entity);


    @Query("delete from RandomExamWordPassedQuestionnaire " +
            "where EXISTS (select 1 from word w where w.languageID=:fromLanguageID " +
                           "and w.wordID = wordID and w.profileID=:profileID) " +
            "and targetTranslationLanguageID=:toLanguageID")
    abstract public void deleteAll(Long profileID, Long fromLanguageID,Long toLanguageID);

    @Query("SELECT * FROM RandomExamWordPassedQuestionnaire p where p.RandomExamPassedQuestionnaireID=:ID")
    abstract public RandomExamWordPassedQuestionnaire findByID(Long ID);


    @Query("SELECT t.* FROM " +
            "(" +
            "    SELECT nWord.* FROM translationwordrelation r INNER JOIN word nWord on r.nativeWordID=nWord.wordID " +
            "INNER JOIN word fWord on r.foreignWordID = fWord.wordID" +

            " where nWord.languageID=:fromLanguageID and fWord.languageID=:toLanguageID " +
            "and nWord.profileID=:profileID " +
            "and not EXISTS (select 1 from RandomExamWordPassedQuestionnaire rExam where rExam.wordID=nWord.wordID and rExam.targetTranslationLanguageID=:toLanguageID) " +
            "and not EXISTS (select 1 from cfexamwordquestionnaire cfexam,cfexamprofilepoint cfexamPP where cfexamPP.CFExamProfilePointID=cfexam.currentCFExamProfilePointID and " +
            "nWord.wordID=cfexam.wordID and cfexam.targetTranslationLanguageID=:toLanguageID and " +
            "cfexamPP.isLoopRepeat!=1 ) " +
            "ORDER BY RANDOM() LIMIT :countNumber" +
            ") t " +
            "ORDER BY t.wordString")
    public abstract List<Word> findAllNativeRandom(Long profileID, Long fromLanguageID, Long toLanguageID, Integer countNumber);

    @Query("SELECT t.* FROM " +
            "(" +
            "    SELECT fWord.* FROM word fWord " +
            " where fWord.languageID=:fromLanguageID " +
            "and EXISTS (select 1 from translationwordrelation r INNER JOIN word nWord on nWord.wordID=r.nativeWordID " +
            "           where r.foreignWordID=fWord.wordID and nWord.languageID=:toLanguageID) " +
            "and not EXISTS (select 1 from RandomExamWordPassedQuestionnaire rExam where rExam.wordID=fWord.wordID and rExam.targetTranslationLanguageID=:toLanguageID) " +
            "and not EXISTS (select 1 from cfexamwordquestionnaire cfexam,cfexamprofilepoint cfexamPP where cfexamPP.CFExamProfilePointID=cfexam.currentCFExamProfilePointID and " +
                            "fWord.wordID=cfexam.wordID and cfexam.targetTranslationLanguageID=:toLanguageID and " +
                            "cfexamPP.isLoopRepeat!=1 ) " +

            "and fWord.profileID=:profileID ORDER BY RANDOM() LIMIT :countNumber" +
            ") t " +
            "ORDER BY t.wordString")
    public abstract List<Word> findAllForeignRandom(Long profileID, Long fromLanguageID, Long toLanguageID, Integer countNumber);


    @Query("SELECT COUNT(1) as total, sum(t.point) as  points FROM " +
            "(" +
            "    SELECT CASE when rExam.RandomExamPassedQuestionnaireID is null then 0 else 1 end as point FROM word fWord " +
            "LEFT JOIN RandomExamWordPassedQuestionnaire rExam ON rExam.wordID=fWord.wordID and rExam.targetTranslationLanguageID=:toLanguageID " +
            " where fWord.languageID=:fromLanguageID " +
            "and EXISTS (select 1 from translationwordrelation r INNER JOIN word nWord on nWord.wordID=r.nativeWordID " +
            "           where r.foreignWordID=fWord.wordID and nWord.languageID=:toLanguageID) " +

            "and not EXISTS (select 1 from cfexamwordquestionnaire cfexam,cfexamprofilepoint cfexamPP where cfexamPP.CFExamProfilePointID=cfexam.currentCFExamProfilePointID and " +
            "fWord.wordID=cfexam.wordID and cfexam.targetTranslationLanguageID=:toLanguageID and " +
            "cfexamPP.isLoopRepeat!=1 ) " +

            "and fWord.profileID=:profileID " +
            ") t ")
    public abstract RandomExamCounter findAllForeignRandomCounter(Long profileID, Long fromLanguageID, Long toLanguageID);


    @Query("SELECT COUNT(1) as total, sum(t.point) as  points FROM " +
            "(" +
            "    SELECT CASE when rExam.RandomExamPassedQuestionnaireID is null then 0 else 1 end as point FROM translationwordrelation r INNER JOIN word nWord on r.nativeWordID=nWord.wordID " +
            "LEFT JOIN RandomExamWordPassedQuestionnaire rExam ON rExam.wordID=nWord.wordID and rExam.targetTranslationLanguageID=:toLanguageID " +
            "INNER JOIN word fWord on r.foreignWordID = fWord.wordID" +

            " where nWord.languageID=:fromLanguageID and fWord.languageID=:toLanguageID " +
            "and nWord.profileID=:profileID " +

            "and not EXISTS (select 1 from cfexamwordquestionnaire cfexam,cfexamprofilepoint cfexamPP where cfexamPP.CFExamProfilePointID=cfexam.currentCFExamProfilePointID and " +
            "nWord.wordID=cfexam.wordID and cfexam.targetTranslationLanguageID=:toLanguageID and " +
            "cfexamPP.isLoopRepeat!=1 ) " +
            ") t ")
    public abstract RandomExamCounter findAllNativeRandomCounter(Long profileID, Long fromLanguageID, Long toLanguageID);






}
