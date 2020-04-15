package com.example.WordCFExam.activity.configureActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.WordCFExam.R;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

public class ConfigPreferenceActivity extends AppCompatActivity {

    EditText pref_cf_fromHour;
    EditText pref_cf_toHour;
    EditText pref_cf_searchRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_preference);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Preferences");

        pref_cf_fromHour = (EditText) findViewById(R.id.et_pref_cf_fromHour);
        pref_cf_toHour = (EditText) findViewById(R.id.et_pref_cf_toHour);
        pref_cf_searchRate = (EditText) findViewById(R.id.et_pref_cf_searchRate);


        pref_cf_fromHour.setText(String.valueOf(Session.getIntAttribute(
                ConfigPreferenceActivity.this, SessionNameAttribute.CfExamEnabledFromHour, 0)));
        pref_cf_toHour.setText(String.valueOf(Session.getIntAttribute(
                ConfigPreferenceActivity.this, SessionNameAttribute.CfExamEnabledToHour,0)));
        pref_cf_searchRate.setText(String.valueOf(Session.getIntAttribute(
                ConfigPreferenceActivity.this, SessionNameAttribute.CfExamSearchRateMinute,0)));

        findViewById(R.id.btn_preferenceSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePreferences();
            }
        });


    }

    private void updatePreferences() {
        Session.setIntAttribute(getApplicationContext(),SessionNameAttribute.CfExamEnabledFromHour,
                Integer.valueOf(pref_cf_fromHour.getText().toString()) );

        Session.setIntAttribute(getApplicationContext(),SessionNameAttribute.CfExamEnabledToHour,
                Integer.valueOf(pref_cf_toHour.getText().toString()) );

        Session.setIntAttribute(getApplicationContext(),SessionNameAttribute.CfExamSearchRateMinute,
                Integer.valueOf(pref_cf_searchRate.getText().toString()) );

        Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

}