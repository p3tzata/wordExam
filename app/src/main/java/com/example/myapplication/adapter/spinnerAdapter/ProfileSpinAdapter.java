package com.example.myapplication.adapter.spinnerAdapter;

import android.content.Context;

import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Profile;

import java.util.List;

public class ProfileSpinAdapter extends BaseSpinAdapter<Profile> {

    public ProfileSpinAdapter(Context context, int textViewResourceId, List<Profile> values) {
        super(context, textViewResourceId, values);
    }

}
