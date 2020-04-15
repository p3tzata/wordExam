package com.example.WordCFExam.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStartCFExamBroadcastReceiver extends BroadcastReceiver
{
    CFExamAlarm cfExamAlarm = new CFExamAlarm();
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
           cfExamAlarm.setAlarm(context);
        }
    }
}
