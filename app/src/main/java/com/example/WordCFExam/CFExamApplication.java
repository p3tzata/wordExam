package com.example.WordCFExam;

import android.app.Application;

import com.example.WordCFExam.database.DatabaseClient;
import com.example.WordCFExam.database.WordRoomDatabase;
import com.example.WordCFExam.factory.FactoryUtil;
import com.example.WordCFExam.service.exam.CFExamTopicQuestionnaireService;
import com.example.WordCFExam.service.exam.CFExamWordQuestionnaireService;

public class CFExamApplication extends Application {

    public static WordRoomDatabase appDatabase;
    public static CFExamWordQuestionnaireService cfExamWordQuestionnaireService;
    public static CFExamTopicQuestionnaireService cfExamTopicQuestionnaireService;

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        cfExamWordQuestionnaireService= FactoryUtil.createCFExamQuestionnaireService(this);
        cfExamTopicQuestionnaireService= FactoryUtil.createCFExamTopicQuestionnaireService(this);

    }


}
