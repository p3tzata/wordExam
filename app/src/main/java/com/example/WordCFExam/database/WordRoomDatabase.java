package com.example.WordCFExam.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.WordCFExam.dao.CFExamProfileDao;
import com.example.WordCFExam.dao.CFExamProfilePointDao;
import com.example.WordCFExam.dao.CFExamScheduleDao;
import com.example.WordCFExam.dao.CFExamTopicQuestionnaireDao;
import com.example.WordCFExam.dao.CFExamWordQuestionnaireDao;
import com.example.WordCFExam.dao.HelpSentenceDao;
import com.example.WordCFExam.dao.LanguageDao;
import com.example.WordCFExam.dao.PartOfSpeechDao;
import com.example.WordCFExam.dao.ProfileDao;
import com.example.WordCFExam.dao.RandomExamHelpSentencePassedQuestionnaireDao;
import com.example.WordCFExam.dao.RandomExamWordPassedQuestionnaireDao;
import com.example.WordCFExam.dao.TopicDao;
import com.example.WordCFExam.dao.TopicTypeDao;
import com.example.WordCFExam.dao.TranslationDao;
import com.example.WordCFExam.dao.TranslationWordRelationDao;
import com.example.WordCFExam.dao.WordDao;
import com.example.WordCFExam.dao.WordFormDao;
import com.example.WordCFExam.dao.WordPartOfSpeechDao;
import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.entity.PartOfSpeech;
import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.entity.Translation;
import com.example.WordCFExam.entity.TranslationWordRelation;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.WordForm;
import com.example.WordCFExam.entity.WordPartOfSpeech;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamProfilePoint;
import com.example.WordCFExam.entity.exam.CFExamSchedule;
import com.example.WordCFExam.entity.exam.CFExamTopicQuestionnaire;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaire;
import com.example.WordCFExam.entity.exam.RandomExamHelpSentencePassedQuestionnaire;
import com.example.WordCFExam.entity.exam.RandomExamWordPassedQuestionnaire;
import com.example.WordCFExam.entity.Topic;
import com.example.WordCFExam.entity.TopicType;

@Database(entities = {CFExamSchedule.class, Topic.class, TopicType.class, CFExamTopicQuestionnaire.class, RandomExamHelpSentencePassedQuestionnaire.class, RandomExamWordPassedQuestionnaire.class,CFExamProfile.class, CFExamProfilePoint.class, CFExamWordQuestionnaire.class, WordForm.class,HelpSentence.class,WordPartOfSpeech.class,PartOfSpeech.class, Profile.class, Language.class, Translation.class, Word.class, TranslationWordRelation.class},
        version =13, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract CFExamScheduleDao cfExamScheduleDao();

    public abstract RandomExamHelpSentencePassedQuestionnaireDao randomExamHelpSentencePassedQuestionnaireDao();

    public abstract RandomExamWordPassedQuestionnaireDao randomExamWordPassedQuestionnaireDao();

    public abstract TopicDao topicDao();

    public abstract TopicTypeDao topicTypeDao();

    public abstract CFExamProfileDao cfExamProfileDao();

    public abstract CFExamProfilePointDao cfExamProfilePointDao();

    public abstract CFExamWordQuestionnaireDao cfExamQuestionnaireDao();

    public abstract CFExamTopicQuestionnaireDao cfExamTopicQuestionnaireDao();

    public abstract HelpSentenceDao helpSentenceDao();

    public abstract WordFormDao wordFormDao();

    public abstract WordPartOfSpeechDao wordPartOfSpeechDao();

    public abstract PartOfSpeechDao partOfSpeechDao();

    public abstract TranslationWordRelationDao translationWordRelationDao();

    public abstract WordDao wordDao();

    public abstract ProfileDao profileDao();

    public abstract LanguageDao languageDao();

    public abstract TranslationDao transactionDao();




}



