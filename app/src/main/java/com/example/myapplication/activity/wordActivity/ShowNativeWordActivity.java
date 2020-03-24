package com.example.myapplication.activity.wordActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.entity.dto.TranslationAndLanguages;

public class ShowNativeWordActivity extends AppCompatActivity {

    TranslationAndLanguages translationAndLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_native_word);
        Intent i = getIntent();
        this.translationAndLanguages = (TranslationAndLanguages) i.getSerializableExtra("translationAndLanguages");
        getSupportActionBar().setTitle(translationAndLanguages.getNativeLanguage().getLanguageName());

    }
}
