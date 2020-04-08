package com.example.myapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.dao.CFExamProfileDao;
import com.example.myapplication.dao.CFExamProfilePointDao;
import com.example.myapplication.dao.CFExamTopicQuestionnaireDao;
import com.example.myapplication.dao.CFExamWordQuestionnaireDao;
import com.example.myapplication.dao.HelpSentenceDao;
import com.example.myapplication.dao.LanguageDao;
import com.example.myapplication.dao.PartOfSpeechDao;
import com.example.myapplication.dao.ProfileDao;
import com.example.myapplication.dao.TopicDao;
import com.example.myapplication.dao.TopicTypeDao;
import com.example.myapplication.dao.TranslationDao;
import com.example.myapplication.dao.TranslationWordRelationDao;
import com.example.myapplication.dao.WordDao;
import com.example.myapplication.dao.WordFormDao;
import com.example.myapplication.dao.WordPartOfSpeechDao;
import com.example.myapplication.dao.RandomExamPassedQuestionnaireDao;
import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.PartOfSpeech;
import com.example.myapplication.entity.Profile;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.WordForm;
import com.example.myapplication.entity.WordPartOfSpeech;
import com.example.myapplication.entity.exam.CFExamProfile;
import com.example.myapplication.entity.exam.CFExamProfilePoint;
import com.example.myapplication.entity.exam.CFExamTopicQuestionnaire;
import com.example.myapplication.entity.exam.CFExamWordQuestionnaire;
import com.example.myapplication.entity.exam.RandomExamPassedQuestionnaire;
import com.example.myapplication.entity.exam.Topic;
import com.example.myapplication.entity.exam.TopicType;

@Database(entities = {Topic.class, TopicType.class, CFExamTopicQuestionnaire.class, RandomExamPassedQuestionnaire.class,CFExamProfile.class, CFExamProfilePoint.class, CFExamWordQuestionnaire.class, WordForm.class,HelpSentence.class,WordPartOfSpeech.class,PartOfSpeech.class, Profile.class, Language.class, Translation.class, Word.class, TranslationWordRelation.class},
        version =5, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {



    public abstract RandomExamPassedQuestionnaireDao randomExamPassedQuestionnaireDao();

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



