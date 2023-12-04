package com.example.WordCFExam.activity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.WordCFExam.R;
import com.example.WordCFExam.adapter.spinnerAdapter.LanguageSpinAdapter;
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.LanguageService;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;
import com.example.WordCFExam.utitliy.TextToSpeechUtil;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class TextToSpeechActivity extends AppCompatActivity {
    Toast m_currentToast;
    private TextToSpeechUtil textToSpeechUtil;
    private LanguageSpinAdapter foreignSpinnerAdapter;
    private LanguageService languageService;
    private Spinner spn_item_foreign;
    AtomicInteger currentSentenceIndex = new AtomicInteger(0);
    boolean isStopButtonPushed = false;
    boolean isPauseButtonPushed = false;
    boolean isPreviousButtonPushed = false;
    boolean isNextButtonPushed = false;
    ZonedDateTime previousButtonPushedTime = ZonedDateTime.now();
    ZonedDateTime nextButtonPushedTime = ZonedDateTime.now();
    String[] sentences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.languageService = FactoryUtil.createLanguageService(getApplication());

        getSupportActionBar().setTitle("Text to Speech");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText et_textToSpeak = (EditText) findViewById(R.id.et_textToSpeak);
        et_textToSpeak.setMaxLines(15);
        DbExecutorImp<List<Language>> dbExecutor = FactoryUtil.<List<Language>>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<List<Language>>() {
            @Override
            public List<Language> doInBackground() {
                return languageService.findAllOrderAlphabetic(0L, "");
            }

            @Override
            public void onPostExecute(List<Language> item) {

                ArrayList<Language> foreignLanguages = new ArrayList<>();

                for (Language language :
                        item) {
                    if (language.getLocaleLanguageTag() != null) {
                        foreignLanguages.add(language);
                    }

                }


                Language foreignBlankLanguage = new Language();
                foreignBlankLanguage.setLanguageID(-1L);
                foreignBlankLanguage.setLanguageName("...");
                foreignLanguages.add(0, foreignBlankLanguage);

                foreignSpinnerAdapter = new LanguageSpinAdapter(TextToSpeechActivity.this,
                        R.layout.spinner_item,
                        foreignLanguages);

                spn_item_foreign = (Spinner) findViewById(R.id.spn_foreignLanguage);
                spn_item_foreign.setAdapter(foreignSpinnerAdapter);

                spn_item_foreign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int selectedForeignItemPosition = spn_item_foreign.getSelectedItemPosition();
                        Language language = foreignSpinnerAdapter.getItem(selectedForeignItemPosition);
                        if (language.getLanguageID() > 0) {
                            Locale locale;
                            try {

                                textToSpeechUtil = new TextToSpeechUtil(language, TextToSpeechActivity.this);
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

        attachButtonListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeechUtil != null) {
            textToSpeechUtil.stop();
        }
        setBrightness(0.5F);
    }

    private void attachButtonListeners() {

        findViewById(R.id.btn_textToSpeechPause).setOnClickListener(v -> {
            if (textToSpeechUtil != null) {
                textToSpeechUtil.stop();
                isPauseButtonPushed = true;
            }
        });

        findViewById(R.id.btn_textToSpeechStop).setOnClickListener(v -> {
            if (textToSpeechUtil != null) {
                textToSpeechUtil.stop();
                isStopButtonPushed = true;
                currentSentenceIndex.set(0);

                setBrightness(0.5F);
            }
        });

        findViewById(R.id.btn_textToSpeechClear).setOnClickListener(v -> {
            if (textToSpeechUtil != null) {
                textToSpeechUtil.stop();
                isStopButtonPushed = true;
                currentSentenceIndex.set(0);
                sentences = null;
                EditText et_textToSpeak = (EditText) findViewById(R.id.et_textToSpeak);
                et_textToSpeak.setText("");
                setBrightness(0.5F);
            }
        });

        findViewById(R.id.btn_textToSpeechPrevious).setOnClickListener(v -> {
            if (textToSpeechUtil != null) {
                textToSpeechUtil.stop();
                isPreviousButtonPushed = true;
                previousButtonPushedTime = ZonedDateTime.now();

                currentSentenceIndex.getAndDecrement();

                if (currentSentenceIndex.get() < 0) {
                    currentSentenceIndex.set(0);
                }
                if (m_currentToast != null) {
                    m_currentToast.cancel();
                }
                if (sentences != null) {
                    m_currentToast = Toast.makeText(getApplicationContext(),
                            sentences[currentSentenceIndex.get()].substring(0, Math.min(sentences[currentSentenceIndex.get()].length() - 1, 20)) + "...",
                            Toast.LENGTH_LONG);

                    m_currentToast.show();
                }
            }
        });

        findViewById(R.id.btn_textToSpeechNext).setOnClickListener(v -> {
            if (textToSpeechUtil != null) {
                textToSpeechUtil.stop();
                isNextButtonPushed = true;
                nextButtonPushedTime = ZonedDateTime.now();

                currentSentenceIndex.getAndIncrement();


                if (m_currentToast != null) {
                    m_currentToast.cancel();
                }
                if (sentences != null) {
                    if (currentSentenceIndex.get() >= sentences.length) {
                        currentSentenceIndex.set(sentences.length - 1);
                    }

                    m_currentToast = Toast.makeText(getApplicationContext(),
                            sentences[currentSentenceIndex.get()].substring(0, Math.min(sentences[currentSentenceIndex.get()].length() - 1, 20)) + "...",
                            Toast.LENGTH_LONG);

                    m_currentToast.show();
                }
            }
        });

        findViewById(R.id.btn_textToSpeechPlay).setOnClickListener(v -> {

            if (textToSpeechUtil != null) {
                EditText et_textToSpeak = (EditText) findViewById(R.id.et_textToSpeak);
                EditText et_miniPause = (EditText) findViewById(R.id.et_TextToSpeechMiniPause);
                String miniPauseString = et_miniPause.getText().toString().equals("") ? "0" : et_miniPause.getText().toString();
                Double miniPauseSec = Double.valueOf(miniPauseString);

                String textToSpeak = et_textToSpeak.getText().toString();
                sentences = textToSpeechUtil.splitTextToSentences(textToSpeak);

                new Thread(() -> {
                    // do background stuff here
                    if (currentSentenceIndex.get() >= sentences.length) {
                        currentSentenceIndex.set(0);
                    }
                    try {
                        this.playHandler(sentences, miniPauseSec);

                        CheckBox chkb_isFinish = (CheckBox) findViewById(R.id.chkb_textToSpeechFinish);
                        if (chkb_isFinish.isChecked()){
                            finish();
                        }

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    runOnUiThread(() -> {
                        // OnPostExecute stuff here
                    });
                }).start();
                setBrightness(0F);

                // textToSpeechUtil.speak(textToSpeak, "speaking");
            } else {
                Toast.makeText(getApplicationContext(), "Select Language", Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (textToSpeechUtil != null) {
                    textToSpeechUtil.shutdown();
                }
                finish();
                return true;
        }
        return true;
    }

    public void playHandler(String[] sentences, double miniPauseSec) throws InterruptedException {
        while (currentSentenceIndex.get() < sentences.length) {

            textToSpeechUtil.speakSentence(sentences, currentSentenceIndex.get(), miniPauseSec, 0.3);

            while (textToSpeechUtil.isSpeaking()) {
                Thread.sleep(100L);
            }

            if (isStopButtonPushed) {
                currentSentenceIndex.set(0);
                isStopButtonPushed = false;
                break;
            } else if (isPauseButtonPushed) {
                isPauseButtonPushed = false;
                break;
            } else if (isPreviousButtonPushed) {

                while (previousButtonPushedTime.isAfter(ZonedDateTime.now().minusSeconds(2L))) {
                    Thread.sleep(100L);
                }
                isPreviousButtonPushed = false;
            } else if (isNextButtonPushed) {
                while (nextButtonPushedTime.isAfter(ZonedDateTime.now().minusSeconds(2L))) {
                    Thread.sleep(100L);
                }
                isNextButtonPushed = false;
            } else {
                currentSentenceIndex.incrementAndGet();
            }
        }

    }

   private void setBrightness(Float target) {
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = target;
        getWindow().setAttributes(layout);
    }

}
