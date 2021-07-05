package com.example.WordCFExam.activity.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.wordActivity.ShowForeignWordActivity;
import com.example.WordCFExam.activity.wordActivity.ShowNativeWordActivity;
import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaireCross;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.LanguageService;
import com.example.WordCFExam.service.TranslationService;
import com.example.WordCFExam.service.exam.CFExamWordQuestionnaireService;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;
import com.example.WordCFExam.utitliy.TextToSpeechUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CFExamWordProceedQuestionActivity extends AppCompatActivity {
    private CFExamWordQuestionnaireCross cfExamQuestionnaireCross;
    private TranslationService translationService;
    private LanguageService languageService;
    private CFExamWordQuestionnaireService cfExamWordQuestionnaireService;
    private TextToSpeechUtil textToSpeechUtil;
    private boolean isTranslateToForeign;
    private CFExamWordQuestionnaireService cfExamQuestionnaireService;
    private Dialog myDialog;
    TranslationAndLanguages translationAndLanguages;
    Language fromLanguage;
    Language toLanguage;
    private List<HelpSentence> helpSentenceList;
    private int curHelpSentence=-1;
    private String defaultHelpButtonText=null;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_proceed_question);
        cfExamQuestionnaireCross = (CFExamWordQuestionnaireCross) getIntent().getSerializableExtra("CFExamQuestionnaireCross");
        translationService=FactoryUtil.createTranslationService(getApplication());
        cfExamWordQuestionnaireService=FactoryUtil.createCFExamQuestionnaireService(getApplication());
        languageService=FactoryUtil.createLanguageService(getApplication());
        cfExamQuestionnaireService=FactoryUtil.createCFExamQuestionnaireService(getApplication());
        helpSentenceList=new ArrayList<>();
        TextView tx_examWord = (TextView) findViewById(R.id.tx_examWord);
        TextView tx_examTask = (TextView) findViewById(R.id.tx_examTask);
        tx_examTask.setText(String.format("Translate to %s",cfExamQuestionnaireCross.getLanguage().getLanguageName()));
        tx_examWord.setText(cfExamQuestionnaireCross.getWord().getWordString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Translate");
        checkIsTranslateToForeign();
        findTranslationAndLanguage();
        attachButtonOnClick();
        TextView et_checkAnswer = (TextView) findViewById(R.id.et_checkAnswer);


        et_checkAnswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        et_checkAnswer.requestFocus();


    }

    private void findTranslationAndLanguage() {




        DbExecutorImp<TranslationAndLanguages> dbExecutor = FactoryUtil.<TranslationAndLanguages>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<TranslationAndLanguages>() {
            @Override
            public TranslationAndLanguages doInBackground() {
                 fromLanguage=languageService.findByID(cfExamQuestionnaireCross.word.getLanguageID());
                 toLanguage=cfExamQuestionnaireCross.getLanguage();
                if (isTranslateToForeign) {
                    TranslationAndLanguages translationAndLanguages = new TranslationAndLanguages();
                    translationAndLanguages.setForeignLanguage(toLanguage);
                    translationAndLanguages.setNativeLanguage(fromLanguage);
                    translationAndLanguages.setTranslation(null);
                    return translationAndLanguages;
                } else {
                    TranslationAndLanguages translationAndLanguages = new TranslationAndLanguages();
                    translationAndLanguages.setForeignLanguage(fromLanguage);
                    translationAndLanguages.setNativeLanguage(toLanguage);
                    translationAndLanguages.setTranslation(null);
                    return translationAndLanguages;
                }




            }

            @Override
            public void onPostExecute(TranslationAndLanguages item) {

                    translationAndLanguages = item;

                Locale locale = null;
                try {
                    locale = Locale.forLanguageTag(translationAndLanguages.getNativeLanguage().getLocaleLanguageTag());
                    textToSpeechUtil = new TextToSpeechUtil(locale, getApplicationContext());
                } catch (Exception ex) {
                  ; // Toast.makeText(getApplicationContext(), "Warning: Can not identify Language Locate Tag", Toast.LENGTH_LONG).show();
                }





            }
        });


    }

    private void attachButtonOnClick() {
        TextView et_checkAnswer = (TextView) findViewById(R.id.et_checkAnswer);


        findViewById(R.id.btn_examCheckAnswer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbExecutorImp<List<String>> dbExecutor = FactoryUtil.<List<String>>createDbExecutor();
                dbExecutor.execute_(new DbExecutor<List<String>>() {
                    @Override
                    public List<String> doInBackground() {
                        return cfExamQuestionnaireService.examCheckAnswer(isTranslateToForeign,
                                cfExamQuestionnaireCross.getWord(),
                                cfExamQuestionnaireCross.getCfExamQuestionnaire().getTargetTranslationLanguageID(),
                                et_checkAnswer.getText().toString());
                    }

                    @Override
                    public void onPostExecute(List<String> item) {
                        if (item!=null) {
                            //Toast.makeText(getApplicationContext(), "Something gone wrong", Toast.LENGTH_SHORT).show();

                            myDialog = new Dialog(CFExamWordProceedQuestionActivity.this);
                            myDialog.setContentView(R.layout.dialog_list_view);
                            DisplayMetrics metrics = getResources().getDisplayMetrics();
                            int width = metrics.widthPixels;
                            int height = metrics.heightPixels;
                            myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

                            ListView resultList = (ListView) myDialog.findViewById(R.id.dialog_list_view_list);
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CFExamWordProceedQuestionActivity.this,
                                    android.R.layout.simple_list_item_1, android.R.id.text1, item);
                            resultList.setAdapter(adapter);
                            myDialog.show();


                        } else {
                            Toast.makeText(getApplicationContext(), "Something gone wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });








            }
        });

        findViewById(R.id.btn_examHelpSound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (helpSentenceList.size()>0) {

                    if (textToSpeechUtil!=null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        StringBuilder stringBuilderToast = new StringBuilder();
                        /*
                        for (int i=0; i<helpSentenceList.size();i++) {

                            textToSpeechUtil.speak(helpSentenceList.get(i).getSentenceString(),"("+(i+1) + "/"+ (helpSentenceList.size()) + ") " +helpSentenceList.get(i).getSentenceString());
                            if (i!=helpSentenceList.size()-1) {
                                textToSpeechUtil.playSilentUtterance(5);
                            }
                        }

                         */

                        textToSpeechUtil.stop();
                        textToSpeechUtil.playSilentUtterance(1);
                        curHelpSentence++;
                        if (curHelpSentence==helpSentenceList.size()) {
                            curHelpSentence=0;
                        }
                        final Button button = findViewById(R.id.btn_examHelpSound);
                        button.setText("("+ (curHelpSentence+1) +"/" + helpSentenceList.size()+") " +defaultHelpButtonText);

                        textToSpeechUtil.speak(helpSentenceList.get(curHelpSentence).getSentenceString(),"("+(curHelpSentence+1) + "/"+ (helpSentenceList.size()) + ") " +helpSentenceList.get(curHelpSentence).getSentenceString());




                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No sound", Toast.LENGTH_SHORT).show();
                }




            }

        });



        findViewById(R.id.btn_examHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (isTranslateToForeign) {
                    intent = new Intent(getApplicationContext(), ShowNativeWordActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), ShowForeignWordActivity.class);
                }

                intent.putExtra("toLanguage",translationAndLanguages.getNativeLanguage());
                intent.putExtra("fromLanguage",translationAndLanguages.getForeignLanguage());
                intent.putExtra("translationAndLanguages",translationAndLanguages);

                intent.putExtra("translationToLanguageID", cfExamQuestionnaireCross.getCfExamQuestionnaire().getTargetTranslationLanguageID());
                intent.putExtra("translationFromLanguageID", cfExamQuestionnaireCross.getWord().getLanguageID());
                intent.putExtra("word", cfExamQuestionnaireCross.getWord());
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_examPassedFailTotal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
                dbExecutor.execute_(new DbExecutor<Boolean>() {
                    @Override
                    public Boolean doInBackground() {
                        return cfExamQuestionnaireService.examProcessedFailTotal(cfExamQuestionnaireCross.getCfExamQuestionnaire());
                    }

                    @Override
                    public void onPostExecute(Boolean item) {
                        if (!item) {
                            Toast.makeText(getApplicationContext(), "Something gone wrong", Toast.LENGTH_SHORT).show();
                        } {
                            finish();
                        }

                    }
                });
            }
        });

        findViewById(R.id.btn_examPassedFail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
                dbExecutor.execute_(new DbExecutor<Boolean>() {
                    @Override
                    public Boolean doInBackground() {
                        return cfExamQuestionnaireService.examProcessedFail(cfExamQuestionnaireCross.getCfExamQuestionnaire());
                    }

                    @Override
                    public void onPostExecute(Boolean item) {
                        if (!item) {
                            Toast.makeText(getApplicationContext(), "Something gone wrong", Toast.LENGTH_SHORT).show();
                        } {
                            finish();
                        }

                    }
                });
            }
        });


        findViewById(R.id.btn_examPassedPostpone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
                dbExecutor.execute_(new DbExecutor<Boolean>() {
                    @Override
                    public Boolean doInBackground() {
                        return cfExamQuestionnaireService.examProcessedPostpone(cfExamQuestionnaireCross.getCfExamQuestionnaire(),1440);
                    }

                    @Override
                    public void onPostExecute(Boolean item) {
                        if (!item) {
                            Toast.makeText(getApplicationContext(), "Something gone wrong", Toast.LENGTH_SHORT).show();
                        } {
                            finish();
                        }

                    }
                });
            }
        });

        findViewById(R.id.btn_examPassedPostpone168).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
                dbExecutor.execute_(new DbExecutor<Boolean>() {
                    @Override
                    public Boolean doInBackground() {
                        return cfExamQuestionnaireService.examProcessedPostpone(cfExamQuestionnaireCross.getCfExamQuestionnaire(),10080);
                    }

                    @Override
                    public void onPostExecute(Boolean item) {
                        if (!item) {
                            Toast.makeText(getApplicationContext(), "Something gone wrong", Toast.LENGTH_SHORT).show();
                        } {
                            finish();
                        }

                    }
                });
            }
        });



        findViewById(R.id.btn_examPassedOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
                dbExecutor.execute_(new DbExecutor<Boolean>() {
                    @Override
                    public Boolean doInBackground() {
                        return cfExamQuestionnaireService.examProcessedOK(cfExamQuestionnaireCross.getCfExamQuestionnaire());
                    }

                    @Override
                    public void onPostExecute(Boolean item) {
                        if (!item) {
                            Toast.makeText(getApplicationContext(), "Something gone wrong", Toast.LENGTH_SHORT).show();
                        } {
                            finish();
                        }

                    }
                });


            }
        });




    }
    private void getHelpSentence() {
        if (isTranslateToForeign) {
// textToSpeechUtil.speak("test","test");

            Long nativeWordId = cfExamQuestionnaireCross.getWord().getWordID();
            Long toLanguageId = cfExamQuestionnaireCross.getLanguage().getLanguageID();
            Long fromLanguageId = cfExamQuestionnaireCross.getWord().getLanguageID();
            DbExecutorImp<List<HelpSentence>> dbExecutor = FactoryUtil.<List<HelpSentence>>createDbExecutor();
            dbExecutor.execute_(new DbExecutor<List<HelpSentence>>() {
                @Override
                public List<HelpSentence> doInBackground() {
                    return cfExamQuestionnaireService.findHelpSentenceByNativeQuestionWord(nativeWordId, toLanguageId, fromLanguageId);
                }

                @Override
                public void onPostExecute(List<HelpSentence> item) {
                    if (item.size() > 0) {

                        if (textToSpeechUtil != null) {

                            helpSentenceList = item;
                            final Button button = findViewById(R.id.btn_examHelpSound);
                            defaultHelpButtonText=button.getText().toString();
                            button.setText("("+ item.size()+") " +defaultHelpButtonText);
                        }
                    }

                }
            });


        }
    }

    private void checkIsTranslateToForeign() {
        DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<Boolean>() {
            @Override
            public Boolean doInBackground() {

                try {
                    return translationService.isToForeignTranslation(
                            Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID, -1L),
                            cfExamQuestionnaireCross.getWord().getLanguageID(),
                            cfExamQuestionnaireCross.getCfExamQuestionnaire().getTargetTranslationLanguageID()
                    );
                } catch (Exception ex) {
                    return null;
                }


            }

            @Override
            public void onPostExecute(Boolean item) {

                if (item!=null) {
                    isTranslateToForeign=item;
                    findTranslationAndLanguage();
                    getHelpSentence();


                } else {
                    Toast.makeText(getApplicationContext(), "Can not find Translation", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
