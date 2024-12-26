package com.example.WordCFExam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.sax.TextElementListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

    private Handler handler;
    protected static Dialog myDialog;
    TextView tx_dialog_speaking;
    EditText et_textToSpeak;
    TextView tv_textToSpeakHelper;
    TextView tv_totalCountSentences;
    EditText et_TextToSpeech_startFromIndx;
    TextView tv_currentSentenceIndx;
    TextView tv_textToSpeakHelperPrev;
    CheckBox chkb_isFinish;
    CheckBox chkb_isHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text_to_speech);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.languageService = FactoryUtil.createLanguageService(getApplication());

        getSupportActionBar().setTitle("Text to Speech");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_textToSpeak = (EditText) findViewById(R.id.et_textToSpeak);
        tv_textToSpeakHelper = (TextView) findViewById(R.id.tv_textToSpeakHelper);
        tv_textToSpeakHelperPrev = (TextView) findViewById(R.id.tv_textToSpeakHelperPrev);
        tv_totalCountSentences = (TextView) findViewById(R.id.tv_textToSpeach_totalCountSentences);
        tv_currentSentenceIndx = (TextView) findViewById(R.id.tv_textToSpeach_currentSentenceIndx);
        et_TextToSpeech_startFromIndx = (EditText) findViewById(R.id.et_TextToSpeech_startFromIndx);
        tv_textToSpeakHelper.setText("");
        tv_textToSpeakHelperPrev.setText("");

        et_textToSpeak.setMaxLines(3);
        DbExecutorImp<List<Language>> dbExecutor = FactoryUtil.<List<Language>>createDbExecutor();

        chkb_isFinish = findViewById(R.id.chkb_textToSpeechFinish);
        chkb_isHelp = findViewById(R.id.chkb_textToSpeechHelpDialog);

        myDialog = new Dialog(TextToSpeechActivity.this);
        myDialog.setContentView(R.layout.dialog_speach);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        myDialog.getWindow().setLayout((6 * width) / 7, (4 * height) / 10);
        tx_dialog_speaking = myDialog.findViewById(R.id.tx_dialog_speaking);
        handler = new Handler();
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

        attachListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeechUtil != null) {
            textToSpeechUtil.stop();
        }
        setBrightness(0.5F);
    }

    private void attachListeners() {

        et_textToSpeak.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {

                    runOnMainUITread(() ->

                            tv_totalCountSentences.setText(String.valueOf(TextToSpeechUtil.splitTextToSentences(s.toString()).length))

                    );
                }
            }
        });

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
                String pauseString = et_miniPause.getText().toString().equals("") ? "0" : et_miniPause.getText().toString();
                Double pauseSec = Double.valueOf(pauseString);

                String textToSpeak = et_textToSpeak.getText().toString();
                sentences = textToSpeechUtil.splitTextToSentences(textToSpeak);
                tv_textToSpeakHelper.setText("");
                tv_textToSpeakHelperPrev.setText("");
                new Thread(
                        () -> {
                            // do background stuff here
                            if (currentSentenceIndex.get() >= sentences.length) {
                                currentSentenceIndex.set(0);
                            }
                            try {
                                this.playHandler(sentences, pauseSec, getApplicationContext());


                                if (chkb_isFinish.isChecked()) {
                                    //todo remove if fun   finish();
                                }

                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                        }).start();

                if (chkb_isHelp.isChecked()) {
                    setBrightness(0.3F);
                } else {
                    setBrightness(0F);
                }


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

    public void playHandler(String[] sentences, double pauseSec, Context applicationContext) throws InterruptedException {

        if (chkb_isHelp.isChecked()) {
            //todo remove if fun with tv_textToSpeakHelper.  runOnMainUITread(() -> myDialog.show());
        }
        String et_TextToSpeech_startFromIndxText = et_TextToSpeech_startFromIndx.getText().toString();
        if (et_TextToSpeech_startFromIndxText!="") {
            currentSentenceIndex.set(Integer.valueOf(et_TextToSpeech_startFromIndxText)-1);
        }

        while (currentSentenceIndex.get() < sentences.length) {

            runOnMainUITread(() -> tv_currentSentenceIndx.setText(String.valueOf(currentSentenceIndex.get()+1)));

            textToSpeechUtil.speakSentence(sentences, currentSentenceIndex.get(), pauseSec, 0.3);

            if (chkb_isHelp.isChecked()) {
                // todo remove if fun with tv_textToSpeakHelper. runOnMainUITread(() -> tx_dialog_speaking.setText(sentences[currentSentenceIndex.get()]));
                runOnMainUITread(() -> tv_textToSpeakHelper.setText(sentences[currentSentenceIndex.get()]));

                if (currentSentenceIndex.get() >= 1) {
                    runOnMainUITread(() -> tv_textToSpeakHelperPrev.setText(sentences[currentSentenceIndex.get() - 1]));
                }


            }

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

        if (chkb_isHelp.isChecked()) {
            //  todo remove if fun with tv_textToSpeakHelper.   runOnMainUITread(() -> myDialog.dismiss());
        }
    }

    private void setBrightness(Float target) {
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = target;
        getWindow().setAttributes(layout);
    }

    private void runOnMainUITread(Runnable runnable) {
        // This thread runs in the UI
        new Thread(() -> handler.post(runnable)).start();
    }

}
