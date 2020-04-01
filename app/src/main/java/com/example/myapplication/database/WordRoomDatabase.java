package com.example.myapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.dao.HelpSentenceDao;
import com.example.myapplication.dao.LanguageDao;
import com.example.myapplication.dao.PartOfSpeechDao;
import com.example.myapplication.dao.ProfileDao;
import com.example.myapplication.dao.TranslationDao;
import com.example.myapplication.dao.TranslationWordRelationDao;
import com.example.myapplication.dao.WordDao;
import com.example.myapplication.dao.WordFormDao;
import com.example.myapplication.dao.WordPartOfSpeechDao;
import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.PartOfSpeech;
import com.example.myapplication.entity.Profile;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.WordForm;
import com.example.myapplication.entity.WordPartOfSpeech;

@Database(entities = {WordForm.class,HelpSentence.class,WordPartOfSpeech.class,PartOfSpeech.class, Profile.class, Language.class, Translation.class, Word.class, TranslationWordRelation.class},
        version = 2, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {


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



