package com.example.myapplication.adapter.spinnerAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.PartOfSpeech;

import java.util.List;

public class LanguageSpinAdapter extends BaseSpinAdapter<Language> {

    public LanguageSpinAdapter(Context context, int textViewResourceId, List<Language> values) {
        super(context, textViewResourceId, values);
    }

}
