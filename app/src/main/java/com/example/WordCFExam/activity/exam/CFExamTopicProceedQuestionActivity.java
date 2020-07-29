package com.example.WordCFExam.activity.exam;

import android.app.Dialog;
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
import com.example.WordCFExam.entity.exam.CFExamTopicQuestionnaire;
import com.example.WordCFExam.entity.exam.CFExamTopicQuestionnaireCross;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.CFExamTopicQuestionnaireService;
import com.example.WordCFExam.utitliy.DbExecutor;
import com.example.WordCFExam.utitliy.DbExecutorImp;

import java.util.ArrayList;
import java.util.List;

public class CFExamTopicProceedQuestionActivity extends AppCompatActivity {
    private CFExamTopicQuestionnaireCross cfExamTopicQuestionnaireCross;
    private CFExamTopicQuestionnaireService cfExamTopicQuestionnaireService;
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
        cfExamTopicQuestionnaireCross = (CFExamTopicQuestionnaireCross) getIntent().getSerializableExtra("CFExamTopicQuestionnaireCross");

        cfExamTopicQuestionnaireService=FactoryUtil.createCFExamTopicQuestionnaireService(getApplication());
        TextView tx_examWord = (TextView) findViewById(R.id.tx_examWord);
        TextView tx_examTask = (TextView) findViewById(R.id.tx_examTask);
        tx_examTask.setText(String.format("Question:"));
        tx_examWord.setText(cfExamTopicQuestionnaireCross.getTopic().getTopicQuestion());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Do you remember?");

        attachButtonOnClick();


    }

    private void attachButtonOnClick() {
        TextView et_checkAnswer = (TextView) findViewById(R.id.et_checkAnswer);


        findViewById(R.id.btn_examCheckAnswer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                            myDialog = new Dialog(CFExamTopicProceedQuestionActivity.this);
                            myDialog.setContentView(R.layout.dialog_list_view);
                            DisplayMetrics metrics = getResources().getDisplayMetrics();
                            int width = metrics.widthPixels;
                            int height = metrics.heightPixels;
                            myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

                            List<String> item=new ArrayList<>();
                            if (et_checkAnswer.getText().toString().toLowerCase().equals(cfExamTopicQuestionnaireCross.getTopic().getTopicAnswer().toLowerCase())) {
                                item.add("The answer is correct");
                            } else {
                                item.add("The answer is NOT correct");
                            }



                            ListView resultList = (ListView) myDialog.findViewById(R.id.dialog_list_view_list);
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CFExamTopicProceedQuestionActivity.this,
                                    android.R.layout.simple_list_item_1, android.R.id.text1, item);
                            resultList.setAdapter(adapter);
                            myDialog.show();

            }
        });

        findViewById(R.id.btn_examHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog = new Dialog(CFExamTopicProceedQuestionActivity.this);
                myDialog.setContentView(R.layout.dialog_list_view);
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                myDialog.getWindow().setLayout((6 * width)/7, (4 * height)/10);

                List<String> item=new ArrayList<>();

                    item.add(cfExamTopicQuestionnaireCross.getTopic().getTopicAnswer());




                ListView resultList = (ListView) myDialog.findViewById(R.id.dialog_list_view_list);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CFExamTopicProceedQuestionActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, item);
                resultList.setAdapter(adapter);
                myDialog.show();

            }
        });

        findViewById(R.id.btn_examPassedPostpone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
                dbExecutor.execute_(new DbExecutor<Boolean>() {
                    @Override
                    public Boolean doInBackground() {
                        return cfExamTopicQuestionnaireService.examProcessedPostpone(cfExamTopicQuestionnaireCross.getCfExamQuestionnaire(),1440);
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
                        return cfExamTopicQuestionnaireService.examProcessedPostpone(cfExamTopicQuestionnaireCross.getCfExamQuestionnaire(),10080);
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



        findViewById(R.id.btn_examPassedFailTotal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbExecutorImp<Boolean> dbExecutor = FactoryUtil.<Boolean>createDbExecutor();
                dbExecutor.execute_(new DbExecutor<Boolean>() {
                    @Override
                    public Boolean doInBackground() {
                        return cfExamTopicQuestionnaireService.examProcessedFailTotal(cfExamTopicQuestionnaireCross.getCfExamQuestionnaire());
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
                        return cfExamTopicQuestionnaireService.examProcessedFail(cfExamTopicQuestionnaireCross.getCfExamQuestionnaire());
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
                        CFExamTopicQuestionnaire cfExamQuestionnaire = cfExamTopicQuestionnaireCross.getCfExamQuestionnaire();
                        return cfExamTopicQuestionnaireService.examProcessedOK(cfExamQuestionnaire);
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

}
