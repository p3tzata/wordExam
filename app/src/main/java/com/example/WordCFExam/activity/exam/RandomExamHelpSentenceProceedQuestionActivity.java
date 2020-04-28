package com.example.WordCFExam.activity.exam;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WordCFExam.R;
import com.example.WordCFExam.activity.wordActivity.ShowForeignWordActivity;
import com.example.WordCFExam.activity.wordActivity.ShowNativeWordActivity;
import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.exam.RandomExamHelpSentencePassedQuestionnaire;
import com.example.WordCFExam.entity.exam.RandomExamWordPassedQuestionnaire;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.TranslationService;
import com.example.WordCFExam.service.exam.RandomExamHelpSentenceQuestionnaireService;
import com.example.WordCFExam.service.exam.RandomExamWordQuestionnaireService;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RandomExamHelpSentenceProceedQuestionActivity extends AppCompatActivity {

    private Language toLanguage;
    private Language fromLanguage;

    private HelpSentence helpSentence;
    private TranslationService translationService;
    private boolean isTranslateToForeign;
    private RandomExamHelpSentenceQuestionnaireService randomExamHelpSentenceQuestionnaireService;
    private Dialog myDialog;

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

        toLanguage = (Language) getIntent().getSerializableExtra("toLanguage");
        fromLanguage = (Language) getIntent().getSerializableExtra("fromLanguage");
        this.helpSentence = (HelpSentence) getIntent().getSerializableExtra("helpSentence");
        translationService=FactoryUtil.createTranslationService(getApplication());
        randomExamHelpSentenceQuestionnaireService=FactoryUtil.createRandomExamHelpSentenceQuestionnaireService(getApplication());
        TextView tx_examWord = (TextView) findViewById(R.id.tx_examWord);
        TextView tx_examTask = (TextView) findViewById(R.id.tx_examTask);

        tx_examTask.setText(String.format("Translate to %s",toLanguage.getLanguageName()));

        if (toLanguage.getLanguageID().equals(helpSentence.getToLanguageID())) {
            tx_examWord.setText(helpSentence.getSentenceString());
        } else {
            tx_examWord.setText(helpSentence.getSentenceTranslation());
        }




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Translate");
        checkIsTranslateToForeign();
        attachButtonOnClick();


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
                        return randomExamHelpSentenceQuestionnaireService.examCheckAnswer(isTranslateToForeign,
                                helpSentence,
                                et_checkAnswer.getText().toString());
                    }

                    @Override
                    public void onPostExecute(List<String> item) {
                        if (item!=null) {
                            //Toast.makeText(getApplicationContext(), "Something gone wrong", Toast.LENGTH_SHORT).show();

                            myDialog = new Dialog(RandomExamHelpSentenceProceedQuestionActivity.this);
                            myDialog.setContentView(R.layout.dialog_list_view);
                            DisplayMetrics metrics = getResources().getDisplayMetrics();
                            int width = metrics.widthPixels;
                            int height = metrics.heightPixels;
                            myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

                            ListView resultList = (ListView) myDialog.findViewById(R.id.dialog_list_view_list);
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(RandomExamHelpSentenceProceedQuestionActivity.this,
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

        findViewById(R.id.btn_examHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDialog = new Dialog(RandomExamHelpSentenceProceedQuestionActivity.this);
                myDialog.setContentView(R.layout.dialog_list_view);
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

                List<String> item=new ArrayList<>();
                if (isTranslateToForeign) {
                    item.add(helpSentence.getSentenceString());
                } else {
                    item.add(helpSentence.getSentenceTranslation());
                }



                ListView resultList = (ListView) myDialog.findViewById(R.id.dialog_list_view_list);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(RandomExamHelpSentenceProceedQuestionActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, item);
                resultList.setAdapter(adapter);
                myDialog.show();

                /* TODO
                Intent intent;
                if (isTranslateToForeign) {
                    intent = new Intent(getApplicationContext(), ShowNativeWordActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), ShowForeignWordActivity.class);
                }

                intent.putExtra("translationToLanguageID", toLanguage.getLanguageID());
                intent.putExtra("translationFromLanguageID", word.getLanguageID());
                intent.putExtra("word", word);
                startActivity(intent);

                 */

            }
        });

        findViewById(R.id.btn_examPassedFail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
                dbExecutor.execute_(new DbExecutor<Boolean>() {
                    @Override
                    public Boolean doInBackground() {
                        RandomExamHelpSentencePassedQuestionnaire randomHelpSentencePassedQuestionnaire = new RandomExamHelpSentencePassedQuestionnaire();
                        randomHelpSentencePassedQuestionnaire.setHelpSentenceID(helpSentence.getHelpSentenceID());
                        randomHelpSentencePassedQuestionnaire.setTargetTranslationLanguageID(toLanguage.getLanguageID());
                        randomHelpSentencePassedQuestionnaire.setEntryPointDateTime(Calendar.getInstance().getTime());
                        return randomExamHelpSentenceQuestionnaireService.examProcessedFail(randomHelpSentencePassedQuestionnaire);
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
                        RandomExamHelpSentencePassedQuestionnaire randomHelpSentencePassedQuestionnaire = new RandomExamHelpSentencePassedQuestionnaire();
                        randomHelpSentencePassedQuestionnaire.setHelpSentenceID(helpSentence.getHelpSentenceID());
                        randomHelpSentencePassedQuestionnaire.setTargetTranslationLanguageID(toLanguage.getLanguageID());
                        randomHelpSentencePassedQuestionnaire.setEntryPointDateTime(Calendar.getInstance().getTime());
                        return randomExamHelpSentenceQuestionnaireService.examProcessedOK(randomHelpSentencePassedQuestionnaire);
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

    private void checkIsTranslateToForeign() {
        DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
        dbExecutor.execute_(new DbExecutor<Boolean>() {
            @Override
            public Boolean doInBackground() {

                try {
                    return !toLanguage.getLanguageID().equals(helpSentence.getToLanguageID());
                 } catch (Exception ex) {
                    return null;
                }


            }

            @Override
            public void onPostExecute(Boolean item) {

                if (item!=null) {
                    isTranslateToForeign=item;
                } else {
                    Toast.makeText(getApplicationContext(), "Can not find Translation", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
