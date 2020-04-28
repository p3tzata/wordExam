package com.example.WordCFExam.adapter.translationListOnClick;

import android.content.Context;
import android.content.Intent;

import com.example.WordCFExam.activity.exam.RandomExamHelpSentenceQuestionnaireNeedProceedActivity;
import com.example.WordCFExam.activity.exam.RandomExamWordQuestionnaireNeedProceedActivity;
import com.example.WordCFExam.adapter.TranslationListAdapterOnClickExecutor;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;

import java.io.Serializable;

public class ClickFromExamMenuActivity_helpSentence implements Serializable,TranslationListAdapterOnClickExecutor {

        @Override
        public void executeFromForeignItemClick(Context context, TranslationAndLanguages translationAndLanguages) {

            Intent activity2Intent = new Intent(context, RandomExamHelpSentenceQuestionnaireNeedProceedActivity.class);
            activity2Intent.putExtra("toLanguage",translationAndLanguages.getNativeLanguage());
            activity2Intent.putExtra("fromLanguage",translationAndLanguages.getForeignLanguage());
            activity2Intent.putExtra("translationAndLanguages",translationAndLanguages);
            context.startActivity(activity2Intent);

        }

        @Override
        public void executeToForeignItemClick(Context context, TranslationAndLanguages translationAndLanguages) {

            Intent activity2Intent = new Intent(context, RandomExamHelpSentenceQuestionnaireNeedProceedActivity.class);
            activity2Intent.putExtra("translationAndLanguages",translationAndLanguages);
            activity2Intent.putExtra("fromLanguage",translationAndLanguages.getNativeLanguage());
            activity2Intent.putExtra("toLanguage",translationAndLanguages.getForeignLanguage());
            context.startActivity(activity2Intent);

        }



}
