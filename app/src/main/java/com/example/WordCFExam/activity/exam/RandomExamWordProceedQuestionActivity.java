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
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;
import com.example.WordCFExam.entity.exam.RandomExamWordPassedQuestionnaire;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.TranslationService;
import com.example.WordCFExam.service.exam.RandomExamWordQuestionnaireService;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;
import com.example.WordCFExam.utitliy.Session;
import com.example.WordCFExam.utitliy.SessionNameAttribute;

import java.util.Calendar;
import java.util.List;

public class RandomExamWordProceedQuestionActivity extends AppCompatActivity {

    private Language toLanguage;
    private Word word;
    private TranslationService translationService;
    private boolean isTranslateToForeign;
    private RandomExamWordQuestionnaireService randomExamWordQuestionnaireService;
    private Dialog myDialog;
    TranslationAndLanguages translationAndLanguages;

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
        translationAndLanguages = (TranslationAndLanguages) getIntent().getSerializableExtra("translationAndLanguages");
        toLanguage = (Language) getIntent().getSerializableExtra("toLanguage");
        word = (Word) getIntent().getSerializableExtra("word");
        translationService=FactoryUtil.createTranslationService(getApplication());
        randomExamWordQuestionnaireService=FactoryUtil.createRandomExamWordQuestionnaireService(getApplication());
        TextView tx_examWord = (TextView) findViewById(R.id.tx_examWord);
        TextView tx_examTask = (TextView) findViewById(R.id.tx_examTask);
        tx_examTask.setText(String.format("Translate to %s",toLanguage.getLanguageName()));
        tx_examWord.setText(word.getWordString());
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
                        return randomExamWordQuestionnaireService.examCheckAnswer(isTranslateToForeign,
                                word,
                                toLanguage.getLanguageID(),
                                et_checkAnswer.getText().toString());
                    }

                    @Override
                    public void onPostExecute(List<String> item) {
                        if (item!=null) {
                            //Toast.makeText(getApplicationContext(), "Something gone wrong", Toast.LENGTH_SHORT).show();

                            myDialog = new Dialog(RandomExamWordProceedQuestionActivity.this);
                            myDialog.setContentView(R.layout.dialog_list_view);
                            DisplayMetrics metrics = getResources().getDisplayMetrics();
                            int width = metrics.widthPixels;
                            int height = metrics.heightPixels;
                            myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

                            ListView resultList = (ListView) myDialog.findViewById(R.id.dialog_list_view_list);
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(RandomExamWordProceedQuestionActivity.this,
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
                Intent intent;
                if (isTranslateToForeign) {
                    intent = new Intent(getApplicationContext(), ShowNativeWordActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), ShowForeignWordActivity.class);
                }

                intent.putExtra("translationToLanguageID", toLanguage.getLanguageID());
                intent.putExtra("translationFromLanguageID", word.getLanguageID());
                intent.putExtra("translationAndLanguages",translationAndLanguages);
                intent.putExtra("word", word);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_examPassedFail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
                dbExecutor.execute_(new DbExecutor<Boolean>() {
                    @Override
                    public Boolean doInBackground() {
                        RandomExamWordPassedQuestionnaire randomExamPassedQuestionnaire = new RandomExamWordPassedQuestionnaire();
                        randomExamPassedQuestionnaire.setWordID(word.getWordID());
                        randomExamPassedQuestionnaire.setTargetTranslationLanguageID(toLanguage.getLanguageID());
                        randomExamPassedQuestionnaire.setEntryPointDateTime(Calendar.getInstance().getTime());
                        return randomExamWordQuestionnaireService.examProcessedFail(randomExamPassedQuestionnaire);
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
                        RandomExamWordPassedQuestionnaire randomExamPassedQuestionnaire = new RandomExamWordPassedQuestionnaire();
                        randomExamPassedQuestionnaire.setWordID(word.getWordID());
                        randomExamPassedQuestionnaire.setTargetTranslationLanguageID(toLanguage.getLanguageID());
                        randomExamPassedQuestionnaire.setEntryPointDateTime(Calendar.getInstance().getTime());
                        return randomExamWordQuestionnaireService.examProcessedOK(randomExamPassedQuestionnaire);
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
                    return translationService.isToForeignTranslation(
                            Session.getLongAttribute(getApplicationContext(), SessionNameAttribute.ProfileID, -1L),
                            word.getLanguageID(),
                            toLanguage.getLanguageID()
                    );
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
