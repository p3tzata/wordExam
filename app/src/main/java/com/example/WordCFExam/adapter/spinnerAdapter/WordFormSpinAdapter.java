package com.example.WordCFExam.adapter.spinnerAdapter;

import android.content.Context;

import com.example.WordCFExam.entity.WordForm;

import java.util.List;

public class WordFormSpinAdapter extends BaseSpinAdapter<WordForm> {

    public WordFormSpinAdapter(Context context, int textViewResourceId, List<WordForm> values) {
        super(context, textViewResourceId, values);
    }
}
