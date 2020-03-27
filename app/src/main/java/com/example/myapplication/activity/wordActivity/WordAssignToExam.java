package com.example.myapplication.activity.wordActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.dto.TranslationAndLanguages;

public class WordAssignToExam extends AppCompatActivity {

    private Word word;
    private Language targetLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_assign_to_exam);
        Intent i = getIntent();
        this.word = (Word) i.getSerializableExtra("word");
        this.targetLanguage = (Language) i.getSerializableExtra("targetLanguage");




    }
}
