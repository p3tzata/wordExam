package com.example.myapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.dao.LanguageDao;
import com.example.myapplication.dao.ProfileDao;
import com.example.myapplication.dao.TranslationDao;
import com.example.myapplication.dao.TranslationWordRelationDao;
import com.example.myapplication.dao.WordDao;
import com.example.myapplication.dao.WordOldDao;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Profile;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.WordOld;

@Database(entities = {WordOld.class, Profile.class, Language.class, Translation.class, Word.class, TranslationWordRelation.class},
        version = 13, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordOldDao wordOldDao();

    public abstract TranslationWordRelationDao translationWordRelationDao();

    public abstract WordDao wordDao();

    public abstract ProfileDao profileDao();

    public abstract LanguageDao languageDao();

    public abstract TranslationDao transactionDao();




}



