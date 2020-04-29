package com.example.WordCFExam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.configureActivity.ConfigTranslationActivity;
import com.example.WordCFExam.adapter.spinnerAdapter.LanguageSpinAdapter;
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.LanguageService;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;
import com.example.WordCFExam.utitliy.TextToSpeechUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TextToSpeechActivity extends AppCompatActivity {

    private TextToSpeechUtil textToSpeechUtil;
    private LanguageSpinAdapter foreignSpinnerAdapter;
    private LanguageService languageService;
    private Spinner spn_item_foreign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);
        this.languageService=FactoryUtil.createLanguageService(getApplication());

        getSupportActionBar().setTitle("Text to Speech");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DbExecutorImp<List<Language>> dbExecutor = FactoryUtil.<List<Language>>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<List<Language>>() {
            @Override
            public List<Language> doInBackground() {
                return languageService.findAllOrderAlphabetic(0L,"");
            }

            @Override
            public void onPostExecute(List<Language> item) {

                ArrayList<Language> foreignLanguages = new ArrayList<>();

                for (Language language:
                     item) {
                    if (language.getLocaleLanguageTag()!=null) {
                        foreignLanguages.add(language);
                    }

                }


                Language foreignBlankLanguage = new Language();
                foreignBlankLanguage.setLanguageID(-1L);
                foreignBlankLanguage.setLanguageName("...");
                foreignLanguages.add(0,foreignBlankLanguage);

                foreignSpinnerAdapter = new LanguageSpinAdapter(TextToSpeechActivity.this,
                        R.layout.spinner_item,
                        foreignLanguages);

                spn_item_foreign = (Spinner) findViewById(R.id.spn_foreignLanguage);
                spn_item_foreign.setAdapter(foreignSpinnerAdapter);

                spn_item_foreign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int selectedForeignItemPosition = spn_item_foreign.getSelectedItemPosition();
                        Language foreignLanguage = foreignSpinnerAdapter.getItem(selectedForeignItemPosition);
                        if (foreignLanguage.getLanguageID()>0) {
                            Locale locale = null;
                            try {
                                locale = Locale.forLanguageTag(foreignLanguage.getLocaleLanguageTag());
                                 textToSpeechUtil = new TextToSpeechUtil(locale, TextToSpeechActivity.this);
                           } catch (Exception ex) {
                                Toast.makeText(getApplicationContext(), "Warning: Can not identify Language Locate Tag", Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        // sometimes you need nothing here
                    }
                });




            }
        });





        findViewById(R.id.btn_textToSpeechStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    if (textToSpeechUtil != null) {
                        EditText et_textToSpeak = (EditText) findViewById(R.id.et_textToSpeak);
                        String textToSpeak = et_textToSpeak.getText().toString();
                        textToSpeechUtil.speak(textToSpeak, "speaking");
                    }

                else {
                    Toast.makeText(getApplicationContext(), "Select Language", Toast.LENGTH_LONG).show();
                }


            }
        });



        findViewById(R.id.btn_textToSpeechStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (textToSpeechUtil != null) {
                   textToSpeechUtil.stop();
                }




            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (textToSpeechUtil!=null) {
                    textToSpeechUtil.shutdown();
                }
                finish();
                return true;
        }
        return true;
    }
}
