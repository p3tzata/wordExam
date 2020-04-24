package com.example.WordCFExam.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.WordCFExam.dao.base.CrudDao;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.exam.RandomExamPassedQuestionnaire;

import java.util.List;

@Dao
public abstract class RandomExamPassedQuestionnaireDao implements CrudDao<RandomExamPassedQuestionnaire> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(RandomExamPassedQuestionnaire entity);

    @Update
    public abstract Integer update(RandomExamPassedQuestionnaire entity);

    @Delete
    public abstract Integer delete(RandomExamPassedQuestionnaire entity);

    @Query("delete from randomexampassedquestionnaire " +
            "where EXISTS (select 1 from word w where w.languageID=:fromLanguageID " +
                           "and w.wordID = wordID and w.profileID=:profileID) " +
            "and targetTranslationLanguageID=:toLanguageID")
    abstract public void deleteAll(Long profileID, Long fromLanguageID,Long toLanguageID);

    @Query("SELECT * FROM RandomExamPassedQuestionnaire p where p.RandomExamPassedQuestionnaireID=:ID")
    abstract public RandomExamPassedQuestionnaire findByID(Long ID);


    @Query("SELECT t.* FROM " +
            "(" +
            "    SELECT nWord.* FROM translationwordrelation r INNER JOIN word nWord on r.nativeWordID=nWord.wordID " +
            "INNER JOIN word fWord on r.foreignWordID = fWord.wordID" +

            " where nWord.languageID=:fromLanguageID and fWord.languageID=:toLanguageID " +
            "and nWord.profileID=:profileID " +
            "and not EXISTS (select 1 from randomexampassedquestionnaire rExam where rExam.wordID=nWord.wordID and rExam.targetTranslationLanguageID=:toLanguageID) " +
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
            "and not EXISTS (select 1 from randomexampassedquestionnaire rExam where rExam.wordID=fWord.wordID and rExam.targetTranslationLanguageID=:toLanguageID) " +
            "and not EXISTS (select 1 from cfexamwordquestionnaire cfexam,cfexamprofilepoint cfexamPP where cfexamPP.CFExamProfilePointID=cfexam.currentCFExamProfilePointID and " +
                            "fWord.wordID=cfexam.wordID and cfexam.targetTranslationLanguageID=:toLanguageID and " +
                            "cfexamPP.isLoopRepeat!=1 ) " +

            "and fWord.profileID=:profileID ORDER BY RANDOM() LIMIT :countNumber" +
            ") t " +
            "ORDER BY t.wordString")
    public abstract List<Word> findAllForeignRandom(Long profileID, Long fromLanguageID, Long toLanguageID, Integer countNumber);





}
