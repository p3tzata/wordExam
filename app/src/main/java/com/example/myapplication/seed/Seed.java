package com.example.myapplication.seed;

import android.app.Application;
import android.os.AsyncTask;


import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Profile;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.LanguageService;
import com.example.myapplication.service.ProfileService;
import com.example.myapplication.service.TranslationService;
import com.example.myapplication.service.WordService;

public class Seed extends Application {

    private ProfileService profileService;
    private LanguageService languageService;
    private TranslationService translationService;
    private WordService wordService;


    public Seed(){
        this.profileService = FactoryUtil.createProfileService(this);
        this.languageService = FactoryUtil.createLanguageService(this);
        this.translationService = FactoryUtil.createTranslationService(this);
        this.wordService = FactoryUtil.createWordService(this);
    }


    public void seedDB(){

        Profile profile = new Profile();
        profile.setProfileName("Default");
        profile.setProfileDesc("Default Profile");

        Language bulgarianLanguage = new Language();
        bulgarianLanguage.setLanguageName("Bulgarian");

        Language englishLanguage = new Language();
        englishLanguage.setLanguageName("English");

        Translation translation = new Translation();
        translation.setProfileID(1L);
        translation.setTranslationName("Bg_En");
        translation.setTranslationDesc("Bulgarian_English");
        translation.setNativeLanguageID(1L);
        translation.setForeignLanguageID(2L);

        Word outstandingWord = new Word();
        outstandingWord.setWordString("outstanding");
        outstandingWord.setLanguageID(2L);
        outstandingWord.setProfileID(1L);

        Word word1 = new Word();
        word1.setWordString("неизплатен");
        word1.setLanguageID(1L);
        word1.setProfileID(1L);

        Word word2 = new Word();
        word2.setWordString("превъзходен");
        word2.setLanguageID(1L);
        word2.setProfileID(1L);



        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                profileService.insert(profile);
                languageService.insert(bulgarianLanguage);
                languageService.insert(englishLanguage);
                translationService.insert(translation);
                wordService.insert(outstandingWord);
                wordService.insert(word1);
                wordService.insert(word2);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        SaveTask st = new SaveTask();
        st.execute();









    }







}
