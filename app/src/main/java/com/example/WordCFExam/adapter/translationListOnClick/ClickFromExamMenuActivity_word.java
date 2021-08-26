package com.example.WordCFExam.adapter.translationListOnClick;

import android.content.Context;
import android.content.Intent;

import com.example.WordCFExam.activity.exam.RandomExamWordQuestionnaireNeedProceedActivity;
import com.example.WordCFExam.activity.wordActivity.ListWordEditableActivity;
import com.example.WordCFExam.activity.wordActivity.ListWordListableActivity;
import com.example.WordCFExam.adapter.TranslationListAdapterOnClickExecutor;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.utitliy.MenuUtility;

import java.io.Serializable;

public class ClickFromExamMenuActivity_word implements Serializable,TranslationListAdapterOnClickExecutor {

        @Override
        public void executeFromForeignItemClick(Context context, TranslationAndLanguages translationAndLanguages) {

            Intent activity2Intent = new Intent(context, RandomExamWordQuestionnaireNeedProceedActivity.class);
            activity2Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity2Intent.putExtra("toLanguage",translationAndLanguages.getNativeLanguage());
            activity2Intent.putExtra("fromLanguage",translationAndLanguages.getForeignLanguage());
            activity2Intent.putExtra("translationAndLanguages",translationAndLanguages);
            context.startActivity(activity2Intent);

        }

        @Override
        public void executeToForeignItemClick(Context context, TranslationAndLanguages translationAndLanguages) {

            Intent activity2Intent = new Intent(context, RandomExamWordQuestionnaireNeedProceedActivity.class);
            activity2Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity2Intent.putExtra("translationAndLanguages",translationAndLanguages);
            activity2Intent.putExtra("fromLanguage",translationAndLanguages.getNativeLanguage());
            activity2Intent.putExtra("toLanguage",translationAndLanguages.getForeignLanguage());
            context.startActivity(activity2Intent);

        }



}
