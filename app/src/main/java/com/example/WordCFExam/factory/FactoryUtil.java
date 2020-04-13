package com.example.WordCFExam.factory;

import android.app.Application;


import com.example.WordCFExam.service.HelpSentenceService;
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
import com.example.WordCFExam.service.exam.CFExamTopicQuestionnaireService;
import com.example.WordCFExam.service.exam.CFExamWordQuestionnaireService;
import com.example.WordCFExam.service.exam.RandomExamWordQuestionnaireService;
import com.example.WordCFExam.service.exam.TopicService;
import com.example.WordCFExam.service.exam.TopicTypeService;
import com.example.WordCFExam.utitliy.DbExecutorImp;

import org.modelmapper.ModelMapper;

public class FactoryUtil {

        static public ProfileService createProfileService(Application application) {
            return new ProfileService(application);
        }

       static public LanguageService createLanguageService(Application application) {
           return new LanguageService(application);
       }

       static public TranslationService createTranslationService(Application application) {
         return new TranslationService(application);
       }

    static public WordService createWordService(Application application) {
        return new WordService(application);
    }

    static public HelpSentenceService createHelpSentenceService(Application application) {
        return new HelpSentenceService(application);
    }

    static public WordFormService createWordFormService(Application application) {
        return new WordFormService(application);
    }


    static public TranslationWordRelationService createTranslationWordRelationService(Application application) {
        return new TranslationWordRelationService(application);
    }


    static public PartOfSpeechService createPartOfSpeechService(Application application) {
        return new PartOfSpeechService(application);
    }

    static public WordPartOfSpeechService createWordPartOfSpeechService(Application application) {
        return new WordPartOfSpeechService(application);
    }

    static public CFExamProfileService createCFExamProfileService(Application application) {
        return new CFExamProfileService(application);
    }

    static public CFExamProfilePointService createCFExamProfilePointService(Application application) {
        return new CFExamProfilePointService(application);
    }

    static public CFExamWordQuestionnaireService createCFExamQuestionnaireService(Application application) {
        return new CFExamWordQuestionnaireService(application);
    }
    static public RandomExamWordQuestionnaireService createRandomExamWordQuestionnaireService(Application application) {
        return new RandomExamWordQuestionnaireService(application);
    }


    static public CFExamTopicQuestionnaireService createCFExamTopicQuestionnaireService(Application application) {
        return new CFExamTopicQuestionnaireService(application);
    }


    static  public TopicService createTopicService(Application application) {
            return new TopicService(application);
    }

    static  public TopicTypeService createTopicTypeService(Application application) {
        return new TopicTypeService(application);
    }




    static public ModelMapper createModelMapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }


    static public <T> DbExecutorImp<T> createDbExecutor(){
            return new DbExecutorImp<T>();
    }


}
