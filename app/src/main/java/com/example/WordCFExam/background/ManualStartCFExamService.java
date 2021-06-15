package com.example.WordCFExam.background;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.WordCFExam.activity.MainActivity;

public class ManualStartCFExamService extends Service
    {
        CFExamAlarm alarm = new CFExamAlarm();
        public void onCreate()
        {
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId)
        {

            alarm.setAlarm(this);
            //alarm.sendNotification_works(this);
            alarm.sendNotification(this,100, MainActivity.class,null,"Schedule CFExam started manual","Started");
            String debug="";
            return START_STICKY;
        }

        @Override
        public void onStart(Intent intent, int startId)
        {


            alarm.setAlarm(this);
            alarm.sendNotification(this,100, MainActivity.class,null,"Schedule CFExam started manual","Started");

        }

        @Override
        public IBinder onBind(Intent intent)
        {
            return null;
        }
    }
