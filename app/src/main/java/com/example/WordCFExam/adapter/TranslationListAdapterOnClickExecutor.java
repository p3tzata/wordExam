package com.example.WordCFExam.adapter;

import android.content.Context;

import com.example.WordCFExam.entity.dto.TranslationAndLanguages;

import java.io.Serializable;

public interface TranslationListAdapterOnClickExecutor extends Serializable {

    void executeFromForeignItemClick(Context context, TranslationAndLanguages translationAndLanguages);

    void executeToForeignItemClick(Context context, TranslationAndLanguages translationAndLanguages);



}
