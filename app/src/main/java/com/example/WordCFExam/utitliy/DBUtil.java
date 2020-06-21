package com.example.WordCFExam.utitliy;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;


import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.entity.PartOfSpeech;
import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.entity.Translation;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.WordForm;
import com.example.WordCFExam.entity.WordPartOfSpeech;
import com.example.WordCFExam.entity.dto.WordCreationDTO;
import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.entity.exam.CFExamProfilePoint;
import com.example.WordCFExam.entity.exam.CFExamSchedule;
import com.example.WordCFExam.entity.exam.CFExamTopicQuestionnaire;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaire;
import com.example.WordCFExam.entity.Topic;
import com.example.WordCFExam.entity.TopicType;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.LanguageService;
import com.example.WordCFExam.service.PartOfSpeechService;
import com.example.WordCFExam.service.ProfileService;
import com.example.WordCFExam.service.TranslationService;
import com.example.WordCFExam.service.TranslationWordRelationService;
import com.example.WordCFExam.service.WordFormService;
import com.example.WordCFExam.service.WordPartOfSpeechService;
import com.example.WordCFExam.service.WordService;
import com.example.WordCFExam.service.exam.CFExamProfilePointService;
import com.example.WordCFExam.service.exam.CFExamProfileService;
import com.example.WordCFExam.service.exam.CFExamScheduleService;
import com.example.WordCFExam.service.exam.CFExamTopicQuestionnaireService;
import com.example.WordCFExam.service.exam.CFExamWordQuestionnaireService;
import com.example.WordCFExam.service.TopicService;
import com.example.WordCFExam.service.TopicTypeService;

import org.modelmapper.ModelMapper;

import java.util.Calendar;

public class DBUtil extends Application {
    private Context context;
    private ProfileService profileService;
    private LanguageService languageService;
    private TranslationService translationService;
    private WordService wordService;
    private TranslationWordRelationService translationWordRelationService;
    private PartOfSpeechService partOfSpeechService;
    private WordPartOfSpeechService wordPartOfSpeechService;
    private WordFormService wordFormService;
    private TopicService topicService;
    private TopicTypeService topicTypeService;
    private CFExamTopicQuestionnaireService cfExamTopicQuestionnaireService;

    private CFExamProfileService cfExamProfileService;
    private CFExamProfilePointService cfExamProfilePointService;
    private CFExamWordQuestionnaireService cfExamQuestionnaireService;
    private CFExamScheduleService cfExamScheduleService;

    public DBUtil(Application context){

//        WordRoomDatabase appDatabase = DatabaseClient.getInstance(context).getAppDatabase();

        this.profileService = FactoryUtil.createProfileService(context);
        this.languageService = FactoryUtil.createLanguageService(this);
        this.translationService = FactoryUtil.createTranslationService(this);
        this.wordService = FactoryUtil.createWordService(this);
        this.translationWordRelationService=FactoryUtil.createTranslationWordRelationService(this);
        this.partOfSpeechService=FactoryUtil.createPartOfSpeechService(this);
        this.wordPartOfSpeechService=FactoryUtil.createWordPartOfSpeechService(this);
        this.wordFormService=FactoryUtil.createWordFormService(this);
        this.cfExamProfileService=FactoryUtil.createCFExamProfileService(this);
        this.cfExamProfilePointService=FactoryUtil.createCFExamProfilePointService(this);
        this.cfExamQuestionnaireService=FactoryUtil.createCFExamQuestionnaireService(this);
        this.topicService = FactoryUtil.createTopicService(this);
        this.topicTypeService = FactoryUtil.createTopicTypeService(this);
        this.cfExamTopicQuestionnaireService=FactoryUtil.createCFExamTopicQuestionnaireService(this);
        this.cfExamScheduleService=FactoryUtil.createCFExamScheduleService(this);
    }




    public void seedDB(){




        Profile profile = new Profile();
        profile.setProfileName("Default");
        profile.setProfileDesc("Default Profile");

        Language bulgarianLanguage = new Language();
        bulgarianLanguage.setLanguageName("Bulgarian");

        Language englishLanguage = new Language();
        englishLanguage.setLanguageName("English");
        englishLanguage.setDefinitionUrl("https://dictionary.cambridge.org/dictionary/english/%s");
        englishLanguage.setLocaleLanguageTag("en-US");

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

        HelpSentence helpSentence = new HelpSentence();
        helpSentence.setWordID(1L);
        helpSentence.setSentenceString("I am good father.");
        helpSentence.setSentenceString("I am father of my son.");

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                /**/



                Profile profileServiceByID = profileService.findByID(1L);
                if (profileServiceByID!=null) {
                    return null;
                }


                profileService.insert(profile);
                languageService.insert(bulgarianLanguage);
                languageService.insert(englishLanguage);
                languageService.insert(franceLanguage);
                translationService.insert(translation);
                translationService.insert(translation1);
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



                //Exam
                cfExamProfileService.insert(new CFExamProfile(){{
                    setName("Default Curve");
                    setProfileID(1L);
                }});

                cfExamProfilePointService.insert(new CFExamProfilePoint(){{
                    setName("1 min.");
                    setCFExamProfileID(1L);
                    setLastOfPeriodInMinute(1L);
                }});

                cfExamProfilePointService.insert(new CFExamProfilePoint(){{
                    setName("3 min.");
                    setCFExamProfileID(1L);
                    setLastOfPeriodInMinute(3L);
                }});

                cfExamProfilePointService.insert(new CFExamProfilePoint(){{
                    setName("5 min.");
                    setCFExamProfileID(1L);
                    setLastOfPeriodInMinute(5L);
                }});



                cfExamQuestionnaireService.insert(new CFExamWordQuestionnaire() {{
                    setCurrentCFExamProfilePointID(1L);
                    setTargetTranslationLanguageID(1L);
                    setWordID(1L);
                    setEntryPointDateTime(Calendar.getInstance().getTime());

                }});

                cfExamQuestionnaireService.insert(new CFExamWordQuestionnaire() {{
                    setCurrentCFExamProfilePointID(1L);
                    setTargetTranslationLanguageID(2L);
                    setWordID(2L);
                    setEntryPointDateTime(Calendar.getInstance().getTime());

                }});



                topicTypeService.insert(new TopicType(){{setProfileID(1L);
                setTopicTypeName("Default topic type");}});

                topicService.insert(new Topic(){{setTopicTypeID(1L);
                setTopicQuestion("What is my name.");
                setTopicAnswer("Какво е твоето име.");}});

                cfExamTopicQuestionnaireService.insert(new CFExamTopicQuestionnaire(){{
                    setCurrentCFExamProfilePointID(1L);
                    setEntryPointDateTime(Calendar.getInstance().getTime());
                    setTopicID(1L);
                }});


                Long insert2 = cfExamScheduleService.insert(new CFExamSchedule() {{
                    setProfileID(1L);
                    setFromHour(8);
                    setToHour(20);
                }});


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
