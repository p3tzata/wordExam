package com.example.myapplication.activity.exam;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.myapplication.R;
import com.example.myapplication.activity.wordActivity.ShowForeignWordActivity;
import com.example.myapplication.activity.wordActivity.ShowNativeWordActivity;
import com.example.myapplication.entity.exam.CFExamWordQuestionnaireCross;
import com.example.myapplication.factory.FactoryUtil;
import com.example.myapplication.service.TranslationService;
import com.example.myapplication.service.exam.CFExamWordQuestionnaireService;
import com.example.myapplication.utitliy.DbExecutor;
import com.example.myapplication.utitliy.DbExecutorImp;
import com.example.myapplication.utitliy.Session;
import com.example.myapplication.utitliy.SessionNameAttribute;

import java.util.List;

public class CFExamWordProceedQuestionActivity extends AppCompatActivity {
    private CFExamWordQuestionnaireCross cfExamQuestionnaireCross;
    private TranslationService translationService;
    private boolean isTranslateToForeign;
    private CFExamWordQuestionnaireService cfExamQuestionnaireService;
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
        cfExamQuestionnaireCross = (CFExamWordQuestionnaireCross) getIntent().getSerializableExtra("CFExamQuestionnaireCross");
        translationService=FactoryUtil.createTranslationService(getApplication());
        cfExamQuestionnaireService=FactoryUtil.createCFExamQuestionnaireService(getApplication());
        TextView tx_examWord = (TextView) findViewById(R.id.tx_examWord);
        TextView tx_examTask = (TextView) findViewById(R.id.tx_examTask);
        tx_examTask.setText(String.format("Translate to %s",cfExamQuestionnaireCross.getLanguage().getLanguageName()));
        tx_examWord.setText(cfExamQuestionnaireCross.getWord().getWordString());
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

        findViewById(R.id.btn_examHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (isTranslateToForeign) {
                    intent = new Intent(getApplicationContext(), ShowNativeWordActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), ShowForeignWordActivity.class);
                }

                intent.putExtra("translationToLanguageID", cfExamQuestionnaireCross.getCfExamQuestionnaire().getTargetTranslationLanguageID());
                intent.putExtra("translationFromLanguageID", cfExamQuestionnaireCross.getWord().getLanguageID());
                intent.putExtra("word", cfExamQuestionnaireCross.getWord());
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
                } else {
                    Toast.makeText(getApplicationContext(), "Can not find Translation", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
