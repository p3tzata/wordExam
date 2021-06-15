package com.example.WordCFExam.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.WordCFExam.activity.MainActivity;

public class AutoStartCFExamBroadcastReceiver extends BroadcastReceiver
{
    CFExamAlarm cfExamAlarm = new CFExamAlarm();
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) 15.06.2021
        //{

           cfExamAlarm.setAlarm(context);
           cfExamAlarm.sendNotification(context,101, MainActivity.class,null,"Schedule CFExam started automatic","Started");
       // }
    }
}
