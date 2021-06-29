package com.example.WordCFExam.adapter.translationListOnClick;

import android.content.Context;
import android.content.Intent;

import com.example.WordCFExam.activity.wordActivity.ListWordEditableActivity;
import com.example.WordCFExam.activity.wordActivity.ListWordListableActivity;
import com.example.WordCFExam.adapter.TranslationListAdapterOnClickExecutor;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.utitliy.MenuUtility;

import java.io.Serializable;

public class ClickFromMainActivity implements Serializable,TranslationListAdapterOnClickExecutor {

        @Override
        public void executeFromForeignItemClick(Context context, TranslationAndLanguages translationAndLanguages) {

            Intent activity2Intent = null;

            Long fromLanguageID=translationAndLanguages.getForeignLanguage().getLanguageID();
            Long toLanguageID=translationAndLanguages.getNativeLanguage().getLanguageID();


            if (MenuUtility.isEditMode(context) && fromLanguageID.equals(translationAndLanguages.getForeignLanguage().getLanguageID())) {
                activity2Intent = new Intent(context, ListWordEditableActivity.class);
            } else {
                activity2Intent = new Intent(context, ListWordListableActivity.class);
            }

            activity2Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity2Intent.putExtra("translationAndLanguages",translationAndLanguages);
            activity2Intent.putExtra("translationFromLanguageID",fromLanguageID);
            activity2Intent.putExtra("translationToLanguageID",toLanguageID);
            context.startActivity(activity2Intent);

        }

        @Override
        public void executeToForeignItemClick(Context context, TranslationAndLanguages translationAndLanguages) {

            Intent activity2Intent = null;

            Long fromLanguageID=translationAndLanguages.getNativeLanguage().getLanguageID();
            Long toLanguageID=translationAndLanguages.getForeignLanguage().getLanguageID();
            if (MenuUtility.isEditMode(context) && fromLanguageID.equals(translationAndLanguages.getForeignLanguage().getLanguageID())) {
                activity2Intent = new Intent(context, ListWordEditableActivity.class);
            } else {
                activity2Intent = new Intent(context, ListWordListableActivity.class);
            }
            activity2Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity2Intent.putExtra("translationAndLanguages",translationAndLanguages);
            activity2Intent.putExtra("translationFromLanguageID",fromLanguageID);
            activity2Intent.putExtra("translationToLanguageID",toLanguageID);
            context.startActivity(activity2Intent);

        }



}
