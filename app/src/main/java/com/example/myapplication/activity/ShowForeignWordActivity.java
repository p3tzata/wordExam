package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.entity.dto.TranslationAndLanguages;

public class ShowForeignWordActivity extends AppCompatActivity {

    TranslationAndLanguages translationAndLanguages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_foreign_word);

        Intent i = getIntent();
        this.translationAndLanguages = (TranslationAndLanguages) i.getSerializableExtra("translationAndLanguages");
        getSupportActionBar().setTitle(translationAndLanguages.getForeignLanguage().getLanguageName());
    }
}
