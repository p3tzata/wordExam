package com.example.myapplication.seed;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;


import com.example.myapplication.database.DatabaseClient;
import com.example.myapplication.database.WordRoomDatabase;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.PartOfSpeech;
import com.example.myapplication.entity.Profile;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.WordForm;
import com.example.myapplication.entity.WordPartOfSpeech;
import com.example.myapplication.entity.dto.WordCreationDTO;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.LanguageService;
import com.example.myapplication.service.PartOfSpeechService;
import com.example.myapplication.service.ProfileService;
import com.example.myapplication.service.TranslationService;
import com.example.myapplication.service.TranslationWordRelationService;
import com.example.myapplication.service.WordFormService;
import com.example.myapplication.service.WordPartOfSpeechService;
import com.example.myapplication.service.WordService;

import org.modelmapper.ModelMapper;

public class Seed extends Application {
    private Context context;
    private ProfileService profileService;
    private LanguageService languageService;
    private TranslationService translationService;
    private WordService wordService;
    private TranslationWordRelationService translationWordRelationService;
    private PartOfSpeechService partOfSpeechService;
    private WordPartOfSpeechService wordPartOfSpeechService;
    private WordFormService wordFormService;
    public Seed(Application context){

//        WordRoomDatabase appDatabase = DatabaseClient.getInstance(context).getAppDatabase();

        this.profileService = FactoryUtil.createProfileService(context);
        this.languageService = FactoryUtil.createLanguageService(this);
        this.translationService = FactoryUtil.createTranslationService(this);
        this.wordService = FactoryUtil.createWordService(this);
        this.translationWordRelationService=FactoryUtil.createTranslationWordRelationService(this);
        this.partOfSpeechService=FactoryUtil.createPartOfSpeechService(this);
        this.wordPartOfSpeechService=FactoryUtil.createWordPartOfSpeechService(this);
        this.wordFormService=FactoryUtil.createWordFormService(this);
    }


    public void seedDB(){

        Profile profile = new Profile();
        profile.setProfileName("Default");
        profile.setProfileDesc("Default Profile");

        Language bulgarianLanguage = new Language();
        bulgarianLanguage.setLanguageName("Bulgarian");

        Language englishLanguage = new Language();
        englishLanguage.setLanguageName("English");

        Language franceLanguage = new Language();
        franceLanguage.setLanguageName("French");

        Translation translation = new Translation();
        translation.setProfileID(1L);
        translation.setTranslationName("Bg_En");
        translation.setTranslationDesc("Bulgarian_English");
        translation.setNativeLanguageID(1L);
        translation.setForeignLanguageID(2L);

        Translation translation1 = new Translation();
        translation1.setProfileID(1L);
        translation1.setTranslationName("Bg_Fr");
        translation1.setTranslationDesc("Bulgarian_French");
        translation1.setNativeLanguageID(1L);
        translation1.setForeignLanguageID(3L);

        Word foreignWordFrench = new Word();
        foreignWordFrench.setWordString("Papa france");
        foreignWordFrench.setLanguageID(3L);
        foreignWordFrench.setProfileID(1L);



        Word foreignWord = new Word();
        foreignWord.setWordString("father");
        foreignWord.setLanguageID(2L);
        foreignWord.setProfileID(1L);

        Word word1 = new Word();
        word1.setWordString("татко");
        word1.setLanguageID(1L);
        word1.setProfileID(1L);

        Word word2 = new Word();
        word2.setWordString("баща");
        word2.setLanguageID(1L);
        word2.setProfileID(1L);

        PartOfSpeech noun_PartOfSpeech = new PartOfSpeech();
        noun_PartOfSpeech.setLanguageID(2L);
        noun_PartOfSpeech.setName("Noun");

        PartOfSpeech verb_PartOfSpeech = new PartOfSpeech();
        verb_PartOfSpeech.setLanguageID(2L);
        verb_PartOfSpeech.setName("Verb");

        WordPartOfSpeech wordPartOfSpeech = new WordPartOfSpeech();
        wordPartOfSpeech.setWordID(1L);
        wordPartOfSpeech.setPartOfSpeechID(1L);

        WordPartOfSpeech wordPartOfSpeech1 = new WordPartOfSpeech();
        wordPartOfSpeech1.setWordID(1L);
        wordPartOfSpeech1.setPartOfSpeechID(2L);

        WordForm wordForm = new WordForm();
        wordForm.setLanguageID(2L);
        wordForm.setWordFormName("Past tense");

        WordForm wordForm1 = new WordForm();
        wordForm1.setLanguageID(2L);
        wordForm1.setWordFormName("Past Perfect tense");

        WordForm wordForm2 = new WordForm();
        wordForm2.setLanguageID(2L);
        wordForm2.setWordFormName("Plural");




        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                /**/
                profileService.insert(profile);
                languageService.insert(bulgarianLanguage);
                languageService.insert(englishLanguage);
                languageService.insert(franceLanguage);
                translationService.insert(translation);
                Long foreignWordID = wordService.insert(foreignWord);
                wordService.insert(word1);
                wordService.insert(word2);
                ModelMapper modelMapper = FactoryUtil.createModelMapper();
                WordCreationDTO wordCreationDTO = modelMapper.map(word1, WordCreationDTO.class);
                WordCreationDTO wordCreationDTO2 = modelMapper.map(word2, WordCreationDTO.class);
                Word word = wordService.findByID(foreignWordID);
                translationWordRelationService.createWordRelation(word,wordCreationDTO);
                translationWordRelationService.createWordRelation(word,wordCreationDTO2);


                partOfSpeechService.insert(noun_PartOfSpeech);
                partOfSpeechService.insert(verb_PartOfSpeech);

                Long insert = wordPartOfSpeechService.insert(wordPartOfSpeech);
                Long insert1 = wordPartOfSpeechService.insert(wordPartOfSpeech1);

                wordFormService.insert(wordForm);
                wordFormService.insert(wordForm1);

                String debug=null;
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
