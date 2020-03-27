package com.example.myapplication.adapter.spinnerAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.entity.PartOfSpeech;
import com.example.myapplication.entity.WordForm;

import java.util.List;

public class WordFormSpinAdapter extends BaseSpinAdapter<WordForm> {

    public WordFormSpinAdapter(Context context, int textViewResourceId, List<WordForm> values) {
        super(context, textViewResourceId, values);
    }
}
